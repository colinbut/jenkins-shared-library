package com.mycompany.colinbut

class AwsCli implements Serializable {

    private final def script

    AwsCli(def script) {
        this.script = script
    }

    String getAwsCliVersionString(){
        return this.script.sh(script: "aws --version", returnStdout: true)
    }
}
