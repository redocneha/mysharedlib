
def call(Map params){
    
pipeline {
    agent any
    options{
        timestamps()
    }
environment {
        DISABLE_AUTH = 'true'
        DB_ENGINE    = 'sqlite'
    }
    stages {
       
        stage('parallel stage'){
           
              parallel{
                stage('Build'){
                    steps{
                         sh 'printenv'
                        echo scm.branches[0].name
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
