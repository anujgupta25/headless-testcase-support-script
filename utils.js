const fs = require("fs");
const axios = require("axios");

function getArg(key) {
  const a = process.argv
    .filter((el) => el.includes(key))
    .toString()
    .replace("--", "")
    .split("=");
  if (a[0] !== key) {
    console.error("error: key not found");
    return false;
  }
  return a[1];
}

function postcomment(body) {
  const token = process.env.GITHUB_TOKEN;
  axios
    .post(
      `https://api.github.com/repos/anujgupta2559/amplify-react-app/pulls/${getArg(
        "AWS_PR_ID"
      )}/reviews`,
      {
        body: body,
        event: "COMMENT",
      },
      {
        headers: {
          Accept: "application/vnd.github+json",
          Authorization: `Bearer ${token}`,
          "User-Agent": "PostmanRuntime/7.29.2",
          "X-GitHub-Api-Version": "2022-11-28",
          "Content-Type": "application/json",
        },
      }
    )
    .then((res) => console.log("success..."))
    .catch((er) => console.log("ERROR....", er.message));
}

const str = fs.createReadStream(
  "./e2e-tests/target/surefire-reports/old/index.html",
  "utf-8"
);

let body = "";

str.on("end", () => {
  const removeLineBreak = body.replace(/[\r\n]/gm, "");
  const final = removeLineBreak.replace(/['"]+/g, "");
  return postcomment(final);
});

str.on("error", (er) => {
  console.log("Error: ", er);
});

str.on("data", (chunk) => {
  body += chunk;
});
