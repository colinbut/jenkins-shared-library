import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification

class BuildJavaAppSpec extends JenkinsPipelineSpecification {
    def buildJavaApp = null

    def setup() {
        buildJavaApp = loadPipelineScriptForTest("vars/buildJavaApp.groovy")
    }

    def "Test run BuildJavaApp pipeline"() {
        setup:
            def body = Mock(Closure)
        when:
            buildJavaApp body
        then:
            1 * getPipelineMock("sh")("./mvnw clean compile")
            1 * getPipelineMock("sh")("./mvnw test")
            1 * getPipelineMock("sh")("./mvnw verify")
            1 * getPipelineMock("sh")("./mvnw package -DskipTests=true")
            1 * body()
    }
}
