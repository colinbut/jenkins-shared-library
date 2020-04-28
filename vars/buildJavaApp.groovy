import com.mycompany.colinbut.Git

def call(Closure body={}) {
    node {
        stage("Checkout") {
            // git credentialsId: 'github_credentials', url: "https://github.com/colinbut/${args.repo}.git"
            new Git(this).checkout("${args.repo}")
        }

        stage("Compile") {
            sh "./mvnw clean compile"
        }

        stage("Unit Test") {
            sh "./mvnw test"
        }

        stage("Integration Test") {
            sh "./mvnw verify"
        }

        stage("Package Artifact Jar") {
            sh "./mvnw package -DskipTests=true"
        }
        body()
    }
}
