def getCurrentBranch () {
    return sh (
        script: 'git rev-parse --abbrev-ref HEAD',
        returnStdout: true
    ).trim()
}
def branchName = getCurrentBranch()

def call(Map params){
    
pipeline {
    agent any
    options{
        timestamps()
    }


echo 'My branch is' + branchName



    stages {
       
        stage('parallel stage'){
           
              parallel{
                stage('Build'){
                    steps{
                       checkout scm
                        echo "My branch is: ${branchName}"
                    }
                }
                 stage('Unit test') {
                    steps {
                        echo 'Unit test done'
                    }
                }
              }
        }
        stage('Sonar') {
           when{ anyOf{branch 'master' ; branch 'release-/*'}}
                    steps {
                        echo 'Sonar Stage done'
                    }
         }
          stage('Docker build') {
              when{ anyOf{branch 'master' ; branch 'release-/*'}}
                    steps {
                        echo 'Docker build stage done'
                    }
         }
          stage('Deploy') {
              when{ anyOf{branch 'master' ; branch 'release-/*'}}
                    steps {
                        echo 'Deploy stage done'
                    }
         }
         stage('BDD') {
             when{ anyOf{branch 'master' ; branch 'release-/*'}}
                    steps {
                        echo ' BDD stage done'
                    }
         }
         stage('Nexus publish') {
             when{ anyOf{branch 'master' ; branch 'release-/*'}}
                    steps {
                        echo 'Nexus publish Stage done'
                    }
         }
        
    }
}


}
