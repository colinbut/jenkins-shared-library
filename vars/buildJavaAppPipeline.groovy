import com.mycompany.colinbut.Git

def call(Map args) {
    node {
        stage("Checkout") {
            new Git(this).checkout(args.repo)
        }
    }
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

def publishToNexus(Map args) {
    node {
        stage("Publish to Nexus") {
            echo "Publishing to Nexus"
        }
    }
    return this
}

def publishToArtifactory(Map args) {
    node {
        stage("Publish to Artifactory") {
            echo "Publishing to Artifactory"
        }
    }
    return this
}

def postBuildSteps(Closure body={}) {
    node {
        body()
    }
}
