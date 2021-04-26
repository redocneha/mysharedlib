def call(Map params){
 
   
pipeline {
    agent any
    options{
        timestamps()
    }
  environment {
    FULL_PATH_BRANCH = "${sh(script:'git name-rev --name-only HEAD', returnStdout: true)}"
    GIT_BRANCH = FULL_PATH_BRANCH.substring(FULL_PATH_BRANCH.lastIndexOf('/') + 1, FULL_PATH_BRANCH.length())
  }


    stages {
       
        stage('parallel stage'){
           
              parallel{
                stage('Build'){
                    steps{
                       echo 'Pulling...' + env.GIT_BRANCH
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
