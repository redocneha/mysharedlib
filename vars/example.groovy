def call(Map params)
{
    pipeline {
    agent any

    environment {
        FOO = GIT_BRANCH.replaceFirst(/^.*\//, '')
    }

    stages {
        stage("Env Variables") {
            environment {
                NAME = "Alan"
            }

            steps {
                echo GIT_BRANCH
                echo "FOO = ${env.FOO}"
                echo "NAME = ${env.NAME}"

             
            }
        }
    }
}
}
