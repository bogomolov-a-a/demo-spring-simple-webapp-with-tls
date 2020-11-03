#!/bin/bash

exchangeFileName=./output.json

function sendSonarRequest() {
  sonarResponseGood=200
  #$(echo -e "$SONAR_QUBE_PROJECT_ISSUE_URL"){
  echo sending sonar request
  code=$(curl -s -o $exchangeFileName -w '%{http_code}' https://sonarcloud.io/api/issues/search?projectKeys=bogomolov-a-a_demo-spring-simple-webapp-with-tls)
  if [[ "$code" -ne "$sonarResponseGood" ]]; then
    echo Code "$code" don\'t valid code for sonar response. Excepted "$sonarResponseGood"
    return 1 # exit from function with error
  fi
  echo sonar request success sent
}

function createIssue() {
  exceptedCode=201
  body=$(echo -e "Cause_$(echo $1 | jq -cr '.message')Rule $(echo $1 | jq -cr '.message')" | tr ' ' '_' | tr ''\''' '_' | tr ''\"'' '_' | tr ':' '_')
  title=$(echo -e $(echo $1 | jq -cr '.message') | tr ' ' '_' | tr ''\''' '_' | tr ''\"'' '_' | tr ':' '_')
  issueData='{"title":"Fix_'$title'","body":"'$body'"}'
  # https://api.github.com/repos/bogomolov-a-a/demo-spring-simple-webapp-with-tls/issues
  echo 'executing "'$githubIssuesCommand'"'
  code=$(curl -X -v -o $exchangeFileName -d '"$issueData"' -H 'Accept: application/vnd.github.v3+json' $(echo -e "$GITHUB_REPO_ISSUE_URL"))
  if [[ "$code" -ne "$exceptedCode" ]]; then
    echo Code "$code" don\'t valid code for github issue response. Excepted "$sonarResponseGood"
    return 1
  fi
  echo '"'$githubIssuesCommand'" success executed'
}

function handleSonarResponse() {
  sonarIssueList=$(cat "$exchangeFileName" | jq -c '.issues[]' | jq -c '{message,pullRequest,rule, component,line,textRange}')
  if [[ $? -ne 0 ]]; then
    return 1
  fi
  while IFS="}}" read -ra issues; do
    for i in "${issues[@]}"; do
      if [[ -n $i ]]; then
        createIssue "$i"'}}'
        if [[ $? -ne 0 ]]; then
          return 1
        fi
      fi
    done
  done <<<"$sonarIssueList"
}
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
