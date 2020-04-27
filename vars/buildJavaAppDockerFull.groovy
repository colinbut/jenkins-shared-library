import com.mycompany.colinbut.DockerEcr

def call(Map args) {
    node {
        stage("Compile") {
            sh "./mvnw clean compile"
        }

        stage("Unit Test") {
            sh "./mvnw test"
        }

        stage("Integration Test") {
            sh "./mvnw verify"
        }

        stage("Static Code Analysis: Sonar") {
            echo "Running static code analysis using Sonar"
            //sh "./mvnw sonar:sonar"

        }

        stage("Package Artifact Jar") {
            sh "./mvnw package -DskipTests=true"
        }

        def dockerEcr = new DockerEcr(this)

        stage("Build Docker Image") {
            dockerEcr.buildDockerImage("${args.microserviceName}")
        }

        stage("Publish Docker Image") {
            dockerEcr.publishDockerImageToECR("${args.microserviceName}")
        }

        stage("Deploying to Dev") {
            echo "Deploying to Dev environment"
        }
    }
}
