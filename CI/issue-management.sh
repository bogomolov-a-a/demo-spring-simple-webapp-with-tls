#!/bin/bash
function createIssue() {
  body=$(echo -e "Cause_$(echo $1 | jq -cr '.message')Rule $(echo $1 | jq -cr '.message')" | tr ' ' '_' | tr ''\''' '_' | tr ''\"'' '_' | tr ':' '_')
  #, )
  title=$(echo -e $(echo $1 | jq -cr '.message') | tr ' ' '_' | tr ''\''' '_' | tr ''\"'' '_' | tr ':' '_')
  issueData='{"title":"Fix_'$title'","body":"'$body'"}'
  githubIssuesCommand="curl -X POST -d '"$issueData"' -H 'Accept: application/vnd.github.v3+json' https://api.github.com/repos/bogomolov-a-a/demo-spring-simple-webapp-with-tls/issues"
  # $(echo -e "$GITHUB_REPO_ISSUE_URL")"
  echo 'executing "'$githubIssuesCommand'"'
  $githubIssuesCommand
  if [[ $? -ne 0 ]]; then
    exit 1
  fi
  echo '"'$githubIssuesCommand'" success executed'
}
sonarCommand='curl  https://sonarcloud.io/api/issues/search?projectKeys=bogomolov-a-a_demo-spring-simple-webapp-with-tls' #$(echo -e "$SONAR_QUBE_PROJECT_ISSUE_URL")
echo 'executing "'$sonarCommand'"'
$sonarCommand >./output.json
if [[ $? -ne 0 ]]; then
  exit 1
fi
sonarIssueList=$(cat ./output.json | jq -c '.issues[]' | jq -c '{message,pullRequest,rule, component,line,textRange}')
if [[ $? -ne 0 ]]; then
  exit 1
fi
while IFS="}}" read -ra issues; do
  for i in "${issues[@]}"; do
    if [[ -n $i ]]; then
      createIssue "$i"'}}'
      if [[ $? -ne 0 ]]; then
        exit 1
      fi
    fi
  done
done <<<"$sonarIssueList"
echo '"'$sonarCommand'" success executed'
