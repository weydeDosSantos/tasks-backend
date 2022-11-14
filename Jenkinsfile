pipeline{
    agent any
    stages{
        stage ('Build Backend'){
            steps{
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Testes unitários'){
            steps{
                bat 'mvn test'
            }
        }
    }
}