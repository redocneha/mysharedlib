def call(Map params)
{
    pipeline {
    agent any

    environment {
        branchName = GIT_BRANCH.replaceFirst(/^.*\//, '')
    }

    stages {
        stage("Env Variables") {
            when { 
                anyOf {
                    expression { branchName ==~ 'main'}
                    expression { branchName ==~ 'master'}
                    
                }
            }
            environment {
                NAME = "Alan"
            }

            steps {
                echo GIT_BRANCH
                echo "FOO = ${branchName}"
                echo "NAME = ${env.NAME}"

             
            }
        }
    }
}
}
