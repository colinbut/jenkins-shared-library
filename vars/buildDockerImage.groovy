import com.mycompany.colinbut.Git

def call(Map args, Closure body={}) {
    node {
        def git = new Git(this)
        stage("Build Docker Image") {
            sh "docker build -t ${args.microserviceName}:${git.commitHash()} ."
        }
        body()
    }
}
