
AWS_PULL_REQUEST_ID=$1
AWS_CLONE_URL=$2

if [ "$AWS_PULL_REQUEST_ID" == "" ] || [ "$AWS_CLONE_URL" == "" ]
then 
    echo "error: parameter is required"
    exit 0
else
    echo "AWS PULL REQUEST ID: ${AWS_PULL_REQUEST_ID}, AWS_CLONE_URL: ${AWS_CLONE_URL}"

    # execute node file
    node utils.js --AWS_PR_ID=$AWS_PULL_REQUEST_ID --AWS_CLONE_URL=$AWS_CLONE_URL
fi