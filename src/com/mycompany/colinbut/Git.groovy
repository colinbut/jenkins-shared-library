package com.mycompany.colinbut

class Git implements Serializable {

    private final def script

    Git(def script) {
        this.script = script
    }

    checkout(String repo) {
        this.script.git credentialsId: Constants.JENKINS_GITHUB_CREDENTIALS_ID, url: "https://github.com/colinbut/${repo}.git"
    }

    String commitHash() {
        return this.script.sh(script: getLatestGitCommitHashCommand(), returnStdOut: true).trim()
    }

    private static String getLatestGitCommitHashCommand() {
        return "git rev-parse HEAD"
    }
}
