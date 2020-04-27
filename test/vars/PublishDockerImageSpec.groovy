import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import com.mycompany.colinbut.DockerEcr

class PublishDockerImageSpec extends JenkinsPipelineSpecification {
    def publishDockerImage = null

    def setup(){
        publishDockerImage = loadPipelineScriptForTest("vars/publishDockerImage.groovy")
    }

    def "Test publish docker image"() {
        setup:
            def dockerEcr = GroovyMock(DockerEcr.class, global: true)
            new DockerEcr(publishDockerImage) >> dockerEcr
        when:
            publishDockerImage awsCliVersion: 1, microserviceName: "microservice-name"
        then:
            1 * dockerEcr.loginToAWSECRDockerRegistry(1)
            1 * dockerEcr.publishDockerImageToECR("microservice-name")
    }

    def "Test run closure block after publish docker image"() {
        setup:
            def body = Mock(Closure)
            def dockerEcr = GroovyMock(DockerEcr.class, global: true)
            new DockerEcr(publishDockerImage) >> dockerEcr
        when:
            publishDockerImage awsCliVersion: 1, microserviceName: "microservice-name", body
        then:
            1 * body()
    }
}