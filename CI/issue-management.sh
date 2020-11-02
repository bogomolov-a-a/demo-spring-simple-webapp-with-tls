#!/bin/bash
function createIssue() {
  body=$(echo -e 'Cause:'"$(echo $1 | jq -cr '.message')\n'Rule:'$(echo $1 | jq -cr '.rule')"\n);
  issueData='{"title":"Fix:'$(echo -e "$(echo $1 | jq -cr '.message')")'","body":"'$body'"''}'
  curl \
    -H "Accept: application/vnd.github.v3+json" \
    ${{github.api_url}}/repos/${{ github.repoisitory }}/issues \
    -d $issueData
    
}
sonarIssueList=$(curl https://sonarcloud.io/api/issues/search?projectKeys=bogomolov-a-a_demo-spring-simple-webapp-with-tls |
  jq -c '.issues[]' | jq -c '{message,pullRequest,rule, component,line,textRange}')
while IFS="}}" read -ra ADDR; do
  for i in "${ADDR[@]}"; do
    if [[ -n $i ]]; then
      createIssue "$i"'}}'
    fi
  done
done <<<"$sonarIssueList"
