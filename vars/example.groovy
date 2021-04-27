def branchName = GIT_BRANCH.replaceFirst(/^.*\//, '')
def call(Map params){
 
pipeline {
    agent any
    options{
        timestamps()
    }
    stages {
       
        stage('parallel stage'){
           
              parallel{
                stage('Build'){
                    steps{
                     echo "{branchName}"
 
                        
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
           when{ anyOf{branch 'master' ; branch 'release-/*' ; branch 'feature-/*'}}
                    steps {
                        echo 'Sonar Stage done'
                    }
         }
          stage('Docker build') {
              when{ anyOf{branch 'master' ; branch 'release-/*' ; branch 'feature-/*'}}
                    steps {
                        echo 'Docker build stage done'
                    }
         }
          stage('Deploy') {
              when{ anyOf{branch 'master' ; branch 'release-/*' ; branch 'feature-/*'}}
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
