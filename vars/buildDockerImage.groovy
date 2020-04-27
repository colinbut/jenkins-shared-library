import com.mycompany.colinbut.DockerEcr

def call(Map args, Closure body={}) {
    node {
        def dockerEcr = new DockerEcr(this)
        stage("Build Docker Image") {
            dockerEcr.buildDockerImage("${args.microserviceName}")
        }
        body()
    }
}
