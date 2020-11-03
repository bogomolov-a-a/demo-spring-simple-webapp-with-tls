#!/bin/bash

exchangeFileName=./output
# send request to sonar qube server, get issue list from it
# and save result into $exchangeFileName file path.
# use curl http_code for control success of the sending.
# if code != 200 (OK) then script fail.
function sendSonarRequest() {
  sonarResponseGood=200
  echo sending sonar request
  code=$(curl -s -o $exchangeFileName -w '%{http_code}' $(echo -e "$SONAR_QUBE_PROJECT_ISSUE_URL"))
  if [[ "$code" -ne "$sonarResponseGood" ]]; then
    echo Code "$code" don\'t valid code for sonar response. Excepted "$sonarResponseGood"
    return 1 # exit from function with error
  fi
  echo sonar request success sent
}
#Create issue from sonar qube issue data.
#Send  POST request to issues api url
#format {"title": "FIX_"+essence_of_the_task, "body":essence_of_the_task+"Rule"+ violation_rule_name+....}
function createGithubIssue() {
  gitHubExceptedCode=201
  echo creating github issue
  title=$(echo -e $(echo $1 | jq -cr '.message') | tr ' ' '_' | tr ''\''' '_' | tr ''\"'' '_' | tr ':' '_')
  echo with title $title
  body=$(echo -e "Cause_$(echo $1 | jq -cr '.message')Rule $(echo $1 | jq -cr '.message')" |
    tr ' ' '_' | tr ''\''' '_' | tr ''\"'' '_' | tr ':' '_')
  echo with body $body
  code=$(curl -s -X -o $exchangeFileName -w '%{http_code}' -d '{"title":"Fix_'$title'","body":"'$body'"}' \
    -H 'Authorization: token '$ISSUES_TOKEN -H 'Accept: application/vnd.github.v3+json' $(echo -e "$GITHUB_REPO_ISSUE_URL"))
  if [[ "$code" -ne "$gitHubExceptedCode" ]]; then
    echo Code "$code" don\'t valid code for github issue response. Excepted "$gitHubExceptedCode"
    cat $exchangeFileName
    return 1
  fi
  echo github issue success created
}
# for each sonar issue create github issue
function handleSonarResponse() {
  sonarIssueList=$(cat "$exchangeFileName" | jq -c '.issues[]' | jq -c '{message,pullRequest,rule, component,line,textRange}')
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
rm $exchangeFileName
