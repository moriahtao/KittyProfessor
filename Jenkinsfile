pipeline {
	agent any
	tools { 
        	maven 'maven-3.5.3'
		jdk 'jdk8'
	}
   stages {
       stage('Build') {
           steps {
               echo "Building"
		sh 'mvn install:install-file -Dfile=./KittyProfessor/jar/jplag-2.11.9-SNAPSHOT-jar-with-dependencies.jar -DgroupId=com.google.code -DartifactId=jplag -Dversion=2.11.9 -Dpackaging=jar'
               sh 'mvn compile -f KittyProfessor/pom.xml'
               sh 'mvn package -f KittyProfessor/pom.xml'
           }
       }
       stage('Test'){
           steps {
               echo "Testing"
               sh 'mvn test -f KittyProfessor/pom.xml'
           }
       }
	stage('SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                        sh 'mvn clean install -f KittyProfessor/pom.xml'
                        sh 'mvn sonar:sonar -f KittyProfessor/pom.xml'
                }
            }
        }
            
        stage('Quality') {
          steps {
            sh 'sleep 30'
            timeout(time: 10, unit: 'SECONDS') {
               retry(5) {
                  script {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
              }
            }
          }
        }
      }
    }
  }
}
