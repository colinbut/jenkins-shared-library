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

    def "Test buildDockerImage"() {
        setup:
            def git = GroovyMock(Git.class, global:true)
            new Git(script) >> git
        when:
            dockerEcr.buildDockerImage("microservice-name")
        then:
            1 * git.commitHash() >> "${COMMIT_HASH}"
            1 * script.sh("docker build -t microservice-name:${COMMIT_HASH} .")
    }
}
