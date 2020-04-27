def call(Closure body={}) {
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

        stage("Package Artifact Jar") {
            sh "./mvnw package -DskipTests=true"
        }
        body()
    }
}
