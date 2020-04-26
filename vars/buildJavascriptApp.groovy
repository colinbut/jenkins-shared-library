def call(Map config=[:], Closure body={}) {
    node {
        git url: config.repo

        stage("Install") {
            sh "npm install"
        }

        stage("Build") {
            sh "npm run build"
        }

        stage("Deploy") {
            if (config.deploy){
                echo "Deploying..."
            }
        }

        body()
    }
}