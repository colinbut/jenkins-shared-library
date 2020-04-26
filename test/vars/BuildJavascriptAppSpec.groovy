import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification

class BuildJavascriptAppSpec extends JenkinsPipelineSpecification {

    def buildJavascriptApp = null
    def repo = "http://github.com/colinbut/example-repo"

    def setup() {
        buildJavascriptApp = loadPipelineScriptForTest("vars/buildJavascriptApp.groovy")
    }

    def "[buildJavascriptApp] will deploy if deploy is true"() {
        when:
            buildJavascriptApp repo: repo, deploy: true
        then:
            1 * getPipelineMock("echo")("Deploying...")
    }


    def "[buildJavascriptApp] will call closure if passed"() {
        setup:
            def body = Mock(Closure)
        when:
            buildJavascriptApp repo: repo, deploy: true, body
        then:
            1 * body()
    }
}
