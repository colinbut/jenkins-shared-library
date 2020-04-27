import com.mycompany.colinbut.DockerEcr

/**
 * Usage:
 *
 * publishDockerImage awsCliVersion: 1, microserviceName: microservice-java
 */
def call(Map args, Closure body={}) {
    def dockerEcr = new DockerEcr(this)
    node {
        stage("Publish Docker Image to ECR") {
            dockerEcr.loginToAWSECRDockerRegistry(args.awsCliVersion)
            dockerEcr.publishDockerImageToECR(args.microserviceName)
        }
        body()
    }
}
