#!/bin/bash
function createIssue() {
  body=$(echo -e 'Cause:'"$(echo $1 | jq -cr '.message')\n'Rule:'$(echo $1 | jq -cr '.rule')"\n)
  issueData='{"title":"Fix:'$(echo -e "$(echo $1 | jq -cr '.message')")'","body":"'$body'"''}'
  curl \
    -H "Accept: application/vnd.github.v3+json" "$GITHUB_REPO_ISSUE_URL" -d $issueData
}
sonarIssueList=$(curl "$SONAR_QUBE_PROJECT_ISSUE_URL" | jq -c '.issues[]' | jq -c '{message,pullRequest,rule, component,line,textRange}')
while IFS="}}" read -ra ADDR; do
  for i in "${ADDR[@]}"; do
    if [[ -n $i ]]; then
      createIssue "$i"'}}'
    fi
  done
done <<<"$sonarIssueList"
