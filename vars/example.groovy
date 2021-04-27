def getBranchName(){
	return GIT_BRANCH.replaceFirst(/^.*\//, '')
}
def call(Map params)
{
    pipeline {
    agent any

    environment {
        branchName = getBranchName()
    }
     stages {
        stage('Parallel Steps'){
	  when { 
                anyOf {
 			expression { branchName ==~ /feature.*/ }
			expression { branchName ==~ 'master'}
			expression { branchName ==~ /^{release}.*/}       
                }
            }
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
		when { 
                anyOf {
                    expression { branchName ==~ 'feature-/*'}
                    expression { branchName ==~ 'master'}
                    expression { branchName ==~ 'release-/*'}       
                }
            }
                    steps {
                        echo 'Sonar Stage done'
                    }
         }
          stage('Docker build') {
     		when { 
                anyOf {
                    expression { branchName ==~ 'feature-/*'}
                    expression { branchName ==~ 'master'}
                    expression { branchName ==~ 'release-/*'}       
                }
            }
                    steps {
                        echo 'Docker build stage done'
                    }
         }
          stage('Deploy') {
          
            when { 
                anyOf {
                    expression { branchName ==~ 'feature-/*'}
                    expression { branchName ==~ 'master'}
                    expression { branchName ==~ 'release-/*'}       
                }
            }
                    steps {
                        echo 'Deploy stage done'
                    }
         }
         stage('BDD') {
             when { 
                anyOf {                  
                    expression { branchName ==~ 'master'}
                    expression { branchName ==~ 'release-/*'}       
                }
            }
                    steps {
                        echo ' BDD stage done'
                    }
         }
         stage('Nexus publish') {
             when { 
                anyOf {
                    expression { branchName ==~ 'master'}
                    expression { branchName ==~ 'release-/*'}       
                }
            }
                    steps {
                        echo 'Nexus publish Stage done'
                    }
         }
        
    }
   
   
}
}
