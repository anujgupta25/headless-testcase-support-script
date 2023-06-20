
AWS_PULL_REQUEST_ID=$1
AWS_CLONE_URL=$2
GITHUB_TOKEN=$3

if [ "$AWS_PULL_REQUEST_ID" == "" ] || [ "$AWS_CLONE_URL" == "" ] || [ "$GITHUB_TOKEN" == "" ]
then 
    echo "error: all parameters are required"
    exit 0
else
    echo "AWS PULL REQUEST ID: ${AWS_PULL_REQUEST_ID}, AWS_CLONE_URL: ${AWS_CLONE_URL}"

    # execute node file
    node utils.js --AWS_PR_ID=$AWS_PULL_REQUEST_ID --AWS_CLONE_URL=$AWS_CLONE_URL --GITHUB_TOKEN=$GITHUB_TOKEN
fi