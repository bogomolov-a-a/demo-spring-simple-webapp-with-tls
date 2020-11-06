#!/bin/bash
exchangeFileName=./output
exchangeGithubFileName=./githubInput.json
jqCurrentItemPrefix='.'
jqIssueListQuery=$jqCurrentItemPrefix'issues[]'
messagePart='message'
rulePart='rule'
componentPart='component'
linePart='line'
severityPart='severity'
typePart='type'
nullValue='null'
jqObjectGenerationQuery='{key,message,type,rule,component,line,severity,line,textRange}'
httpReturnCode='%{http_code}'
automaticCreatedIssueHeader='# Automatic created issue from sonar qube source!\n'
causeMessagePart='**Cause:** '
ruleMessagePart='\n**Rule:** '
componentMessagePart='\n**Component**: '
lineMessagePart='\n**Line:** '
sonarResponseGood=200
fullMessageJsonPrefix='{"title":"Fix: '
fullMessageJsonBodyPrefix='","body":"'
fullMessageJsonLabelPrefix='","labels":['
fullMessageJsonSuffix=']}'
# send request to sonar qube server, get issue list from it
# and save result into $exchangeFileName file path.
# use curl http_code for control success of the sending.
# if code != 200 (OK) then script fail.
function sendSonarRequest() {

  echo sending sonar request
  code=$(curl -s -o $exchangeFileName -w '%{http_code}' $(echo -e "$SONAR_QUBE_PROJECT_ISSUE_URL"))
  if [[ "$code" -ne "$sonarResponseGood" ]]; then
    echo Code "$code" don\'t valid code for sonar response. Excepted "$sonarResponseGood"
    return 1 # exit from function with error
  fi
  echo sonar request success sent
}

function formGithubMessageBody() {
  message=$(echo $1 | jq -cr $jqCurrentItemPrefix$messagePart | tr '"' '*')
  rule=$(echo $1 | jq -cr $jqCurrentItemPrefix$rulePart | tr '"' '*')
  component=$(echo $1 | jq -cr $jqCurrentItemPrefix$componentPart)
  line=$(echo $1 | jq -cr $jqCurrentItemPrefix$linePart)
  echo $automaticCreatedIssueHeader$causeMessagePart \
    $(echo $message)$ruleMessagePart$(echo $rule)$componentMessagePart$(echo $component)$lineMessagePart$line
}
function buildLabels() {
  result='"'$2'"'
  severityLevel=$(echo $1 | jq -cr $jqCurrentItemPrefix$severityPart)
  #
  if [[ "$severityLevel" != "$nullValue" ]]; then
    result=$result',"'$(echo $severityLevel | tr '[:upper:]' '[:lower:]')'"'
  fi
  type=$(echo $1 | jq -cr $jqCurrentItemPrefix$typePart)
  if [[ "$type" != "$nullValue" ]]; then
    result=$result',"'$(echo $type | tr '[:upper:]' '[:lower:]')'"'
  fi
  echo $result
}

function formFullMessageJson() {
  title=$(echo $1 | jq -cr $jqCurrentItemPrefix$messagePart | tr '"' '*')
  body=$(formGithubMessageBody "$1")
  echo $fullMessageJsonPrefix$(echo $title) \
    $fullMessageJsonBodyPrefix$(echo $body)$fullMessageJsonLabelPrefix$(echo $(buildLabels "$1" "$2"))$fullMessageJsonSuffix
}
function createLabel() {
  gitHubExceptedCode=201
  echo creating label $1
  code=$(curl -s -X POST -o $exchangeFileName -w '%{http_code}' -H 'Authorization: token '$GITHUB_TOKEN \
    -H 'Accept: application/vnd.github.v3+json' -d '{"name":"'$key'"}' $(echo -e "$GITHUB_REPO_LABEL_URL"))
  if [[ "$code" -ne "$gitHubExceptedCode" ]]; then
    echo Code "$code" don\'t valid code for github issue response. Excepted "$gitHubExceptedCode"
    cat $exchangeFileName
    rm $exchangeFileName
    return 1
  fi
  rm $exchangeFileName
  echo label $1 created
}
function checkIssueByLabel() {
  gitHubExceptedCode=200
  gitHubExceptedErrorCode=404
  code=$(curl -s -o $exchangeFileName -w '%{http_code}' -H 'Authorization: token '$GITHUB_TOKEN \
    -H 'Accept: application/vnd.github.v3+json' $(echo -e "$GITHUB_REPO_LABEL_URL"'/'$key))
  if [[ "$code" -ne "$gitHubExceptedCode" ]]; then
    echo Code "$code" don\'t valid code for github issue response. Excepted "$gitHubExceptedCode"
    if [[ "$code" -eq "$gitHubExceptedErrorCode" ]]; then
      echo Code "$code" valid error code for github label error response. label not found
      rm $exchangeFileName
      return 1
    fi
    rm $exchangeFileName
    return 2
  fi
  rm $exchangeFileName
  return 0
}
#Create issue from sonar qube issue data.
#Send  POST request to issues api url
#format {"title": "FIX: "+essence_of_the_task, "body":essence_of_the_task+"\nRule:"+ violation_rule_name+....}
function createGithubIssue() {
  gitHubExceptedCode=201
  key=$(echo $(echo $1 | jq -cr '.key' | tr '[:upper:]' '[:lower:]'))
  checkIssueByLabel $key
  returnCode=$?
  if [[ $returnCode -eq 2 ]]; then
    echo can\'t create github issue for sonar key $key
    return 1
  fi
  if [[ $returnCode -eq 0 ]]; then
    echo github issue for sonar key $key already created
    return 0
  fi
  createLabel $key
  echo creating github issue
  fullGithubDataJson=$(formFullMessageJson "$1" "$key")
  echo -n $fullGithubDataJson >$exchangeGithubFileName
  code=$(curl -s -X POST -o $exchangeFileName -w '%{http_code}' -H 'Authorization: token '$GITHUB_TOKEN \
    -H 'Accept: application/vnd.github.v3+json' -d @$exchangeGithubFileName $(echo -e "$GITHUB_REPO_ISSUE_URL"))
  if [[ "$code" -ne "$gitHubExceptedCode" ]]; then
    echo Code "$code" don\'t valid code for github issue response. Excepted "$gitHubExceptedCode"
    cat $exchangeFileName
    rm $exchangeFileName
    return 1
  fi
  #rm $exchangeGithubFileName
  #rm $exchangeFileName
  echo github issue success created
}
# for each sonar issue create github issue
function handleSonarResponse() {
  sonarIssueList=$(cat "$exchangeFileName" | jq -c $jqIssueListQuery |
    jq -c $jqObjectGenerationQuery)
  if [[ $? -ne 0 ]]; then
    return 1
  fi
  while IFS="}}" read -ra issues; do
    for i in "${issues[@]}"; do
      if [[ -n $i ]]; then
        createGithubIssue "$i"'}}'
        if [[ $? -ne 0 ]]; then
          exit 1
        fi
      fi
    done
  done <<<"$sonarIssueList"
}
# main script
sendSonarRequest
if [[ $? -ne 0 ]]; then
  rm $exchangeFileName
  exit $?
fi
handleSonarResponse
if [[ $? -ne 0 ]]; then
  rm $exchangeFileName
  exit $?
fi
#rm $exchangeFileName
