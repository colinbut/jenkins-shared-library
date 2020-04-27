import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import com.mycompany.colinbut.DockerEcr

class BuildDockerImageSpec extends JenkinsPipelineSpecification {
    def buildDockerImage = null

    def setup() {
        buildDockerImage = loadPipelineScriptForTest("vars/buildDockerImage.groovy")
    }

    def "Test build Docker Image"() {
        setup:
            def dockerEcr = GroovyMock(DockerEcr.class, global: true)
            new DockerEcr(buildDockerImage) >> dockerEcr
            def body = Mock(Closure)
        when:
            buildDockerImage microserviceName: "microservice-name", body
        then:
            1 * dockerEcr.buildDockerImage("microservice-name")
            1 * body()
    }
}
