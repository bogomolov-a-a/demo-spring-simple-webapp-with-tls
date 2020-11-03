#!/bin/bash
function createIssue() {
  body=$(echo -e "Cause:$(echo $1 | jq -cr '.message')\nRule:$(echo $1 | jq -cr '.rule')"\n)
  issueData='{\"title\":\"Fix:'$(echo -e "$(echo $1 | jq -cr '.message')")'\",\"body":\"'$body'\"}'
  githubIssuesCommand="curl -H \"Accept: application/vnd.github.v3+json\""$(echo -e "$GITHUB_REPO_ISSUE_URL")"' -d '"$issueData"'"
  echo 'executing "'$githubIssuesCommand'"'
  $githubIssuesCommand
  if [[ $? -ne 0 ]]; then
    exit 1
  fi
  echo '"'$githubIssuesCommand'" success executed'
}
sonarCommand='curl '$(echo -e "$SONAR_QUBE_PROJECT_ISSUE_URL")
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
