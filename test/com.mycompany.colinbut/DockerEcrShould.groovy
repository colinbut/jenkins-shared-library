package com.mycompany.colinbut

import spock.lang.Specification

class DockerEcrShould extends Specification {

    private static final String COMMIT_HASH = "5e68e94b46dcde93def3f93f0da1933000294dc4"

    private DockerEcr dockerEcr
    private def script

    def setup() {
        script = Spy(WorkflowScriptStub)
        dockerEcr = new DockerEcr(script)
    }

    def "Test login to AWS ECR Docker Registry using AWS CLI 1"() {
        when:
            dockerEcr.loginToAWSECRDockerRegistry(1)
        then:
            1 * script.sh(
                    """
                    aws ecr get-login --region eu-west-2 --no-include-email \
                    | awk '{printf \$6}' \
                    | docker login -u AWS https://066203203749.dkr.ecr.eu-west-2.amazonaws.com --password-stdin
                    """
            )
    }

    def "Test login to AWS ECR Docker Registry using AWS CLI 2"() {
        when:
            dockerEcr.loginToAWSECRDockerRegistry(2)
        then:
        1 * script.sh(
                    """
                    aws ecr get-login-password --region eu-west-2 \
                    | docker login -u AWS https://066203203749.dkr.ecr.eu-west-2.amazonaws.com --password-stdin
                    """
        )
    }

    def "Test buildDockerImage"() {
        setup:
            def git = GroovyMock(Git.class, global:true)
            new Git(script) >> git
        when:
            dockerEcr.buildDockerImage("microservice-name")
        then:
            1 * git.commitHash() >> "${COMMIT_HASH}"
            1 * script.sh("docker build -t 066203203749.dkr.ecr.eu-west-2.amazonaws.com/microservice-name:${COMMIT_HASH} .")
    }

    def "Test publishDockerImage"() {
        setup:
            def git = GroovyMock(Git.class, global: true)
            new Git(script) >> git
        when:
            dockerEcr.publishDockerImageToECR("microservice-name")
        then:
            1 * git.commitHash() >> "${COMMIT_HASH}"
            1 * script.sh("docker push 066203203749.dkr.ecr.eu-west-2.amazonaws.com/microservice-name:${COMMIT_HASH}")
    }
}
