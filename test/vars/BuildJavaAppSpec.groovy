import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import com.mycompany.colinbut.Git

class BuildJavaAppSpec extends JenkinsPipelineSpecification {
    def buildJavaApp = null

    def setup() {
        buildJavaApp = loadPipelineScriptForTest("vars/buildJavaApp.groovy")
    }

    def "Test run BuildJavaApp pipeline"() {
        setup:
            def body = Mock(Closure)
            def git = GroovyMock(Git.class, global: true)
            new Git(buildJavaApp) >> git
        when:
            buildJavaApp repo: "http://github.com/colinbut/examplerepo.git", body
        then:
            1 * git.checkout("http://github.com/colinbut/examplerepo.git")
            1 * getPipelineMock("sh")("./mvnw clean compile")
            1 * getPipelineMock("sh")("./mvnw test")
            1 * getPipelineMock("sh")("./mvnw verify")
            1 * getPipelineMock("sh")("./mvnw package -DskipTests=true")
            1 * body()
    }
}
