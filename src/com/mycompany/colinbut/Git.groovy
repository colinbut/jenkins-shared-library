package com.mycompany.colinbut

class Git implements Serializable {

    private final def script

    Git(def script) {
        this.script = script
    }

    String commitHash() {
        return this.script.sh(script: getLatestGitCommitHashCommand(), returnStdOut: true).trim()
    }

    private static String getLatestGitCommitHashCommand() {
        return "git rev-parse HEAD"
    }
}
