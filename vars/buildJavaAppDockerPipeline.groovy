import com.mycompany.colinbut.DockerEcr

def call(Map args=[:]) {
    return this
}

def compile(Map args) {
    node {
        stage("Compile") {
            sh "${args.command}"
        }
    }
    return this
}

def unitTests(Map args) {
    node {
        stage("Unit Tests"){
            sh "${args.command}"
        }
    }
    return this
}

def integrationTests(Map args) {
    node {
        stage("Integration Tests"){
            sh "${args.command}"
        }
    }
    return this
}

def packageArtifact(Map args) {
    node {
        stage("Package Artifact"){
            sh "${args.command}"
        }
    }
    return this
}

def buildDockerImage(Map args) {
    node {
        def dockerEcr = new DockerEcr(this)
        stage("Build Docker Image") {
            dockerEcr.buildDockerImage("${args.microserviceName}")
        }
    }
    return this
}

def publishDockerImage(Map args) {
    node {
        def dockerEcr = new DockerEcr(this)
        stage("Publish Docker Image") {
            dockerEcr.publishDockerImageToECR("${args.microserviceName}")
        }
    }
    return this
}

def additionalPostBuildSteps(Closure body={}) {
    node {
        body()
    }
}

