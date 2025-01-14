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
        stage ('Deploy Backend'){
            steps{
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage ('Teste Api'){
            steps{
                dir('api-test'){
                    git branch: 'main', credentialsId: 'github_login', url: 'https://github.com/weydeDosSantos/tasks-api'
                    bat 'mvn test'          
                }
            }
        }
        stage ('Deploy FrontEnd'){
            steps{
                dir('frontend'){
                    git branch: 'master', credentialsId: 'github_login', url: ' https://github.com/weydeDosSantos/tasks-frontend'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage ('Funcional test'){
            steps{
                dir('funcional-test'){
                    git branch: 'main', credentialsId: 'github_login', url: 'https://github.com/weydeDosSantos/tasks-funcional-testes'
                    bat 'mvn test'          
                }
            }
        }
        stage ('Deploy Prod') {
            steps{
                bat 'docker-compose build'
                bat 'docker-compose up -d' 
            }
        } 
        stage ('Health Check'){
            steps{
                sleep(10)
                dir('funcional-test'){
                    bat 'mvn verify -Dskip.surefire.tests'          
                }
            }
        }   
    } 
    post{
        always{
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, funcional-test/target/surefire-reports/*.xml, funcional-test/target/failsafe-reports/*.xml'
        }
    }  
}
  