import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import com.mycompany.colinbut.Git

class BuildDockerImageSpec extends JenkinsPipelineSpecification {
    def buildDockerImage = null
    private static final String COMMIT_HASH = "5e68e94b46dcde93def3f93f0da1933000294dc4"

    def setup() {
        buildDockerImage = loadPipelineScriptForTest("vars/buildDockerImage.groovy")
    }

    def "Test build Docker Image"() {
        setup:
            def git = GroovyMock(Git.class, global:true)
            new Git(buildDockerImage) >> git

            def body = Mock(Closure)
        when:
            buildDockerImage microserviceName: "microservice-name", body
        then:
            1 * git.commitHash() >> "${COMMIT_HASH}"
            1 * getPipelineMock("sh")("docker build -t microservice-name:${COMMIT_HASH} .")
            1 * body()
    }
}
