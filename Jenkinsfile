pipeline{
    agent any
    stages{
        stage ('Build Backend'){
            steps{
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage ('unit test'){
            steps{
                bat 'mvn test'
            }
        }
         stage ('Sonar Analise'){
            environment{
                scannerHome = tool 'SONAR_SCANNER' 
            }
            steps{
                withSonarQubeEnv('SONAR_LOCAL'){
                bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=a083066c545a7b1e2ee929eae2d6bee45c8c1e0f -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }
        stage ('Quality Gate') {
            steps{
                sleep(30)
                timeout(time:, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }   
}
     