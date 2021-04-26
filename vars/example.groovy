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
                        echo 'Build Stage done'
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
                    steps {
                        echo 'Sonar Stage done'
                    }
         }
          stage('Docker build') {
                    steps {
                        echo 'Docker build stage done'
                    }
         }
          stage('Deploy') {
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
