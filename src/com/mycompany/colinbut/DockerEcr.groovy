package com.mycompany.colinbut

class DockerEcr implements Serializable {

    private final def script

    static final String awsRegion = "eu-west-2"
    static final String dockerUser = "AWS"
    static final String dockerRegistryIdentifier = "066203203749.dkr.ecr.eu-west-2.amazonaws.com"
    static final String dockerRegistryUrl = "https://${dockerRegistryIdentifier}"

    DockerEcr(def script) {
        this.script = script
    }

    void loginToAWSECRDockerRegistry(int awsCliVersion) {
        if (awsCliVersion == 1) {
            this.script.sh(
                    """
                    aws ecr get-login --region ${awsRegion} --no-include-email \
                    | awk '{printf \$6}' \
                    | docker login -u ${dockerUser} ${dockerRegistryUrl} --password-stdin
                    """
            )
        } else if (awsCliVersion == 2) {
            this.script.sh(
                    """
                    aws ecr get-login-password --region ${awsRegion} \
                    | docker login -u ${dockerUser} ${dockerRegistryUrl} --password-stdin
                    """
            )
        }
    }

    void buildDockerImage(String microserviceName) {
        def git = new Git(this.script)
        script.sh("docker build -t ${dockerRegistryIdentifier}/${microserviceName}:${git.commitHash()} .")
    }

    void publishDockerImageToECR(String microserviceName) {
        loginToAWSECRDockerRegistry(1)

        def git = new Git(this.script)
        script.sh("docker push ${dockerRegistryIdentifier}/${microserviceName}:${git.commitHash()}")
    }
}
