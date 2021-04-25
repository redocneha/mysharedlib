def call(String repoUrl) {
  pipeline {
       agent any
      options{
          timestamps()
      }
       stages {
           stage("Tools initialization") {
               steps {
                   echo "1"
               }
           }
           stage("Checkout Code") {
               steps {
                  echo "2"
               }
           }
           stage("Cleaning workspace") {
               steps {
                   echo "3"
               }
           }
           stage("Running Testcase") {
              steps {
                   echo "4"
               }
           }
           stage("Packing Application") {
               steps {
                  echo "5"
               }
           }
       }
   }
}
