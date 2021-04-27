def call(Map params)
{
    pipeline {
    agent any

    environment {
        branchName = GIT_BRANCH.replaceFirst(/^.*\//, '')
    }

    stages {
        stage("Env Variables") {
            when { $env.branchName 'master'}
            environment {
                NAME = "Alan"
            }

            steps {
                echo GIT_BRANCH
                echo "FOO = ${env.branchName}"
                echo "NAME = ${env.NAME}"

             
            }
        }
    }
}
}
