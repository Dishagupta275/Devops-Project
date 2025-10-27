pipeline {
    agent any

    tools { maven 'maven' }

    stages {
        stage('Clone') {
            steps { git 'https://github.com/Dishagupta275/bharatbazaar.git' }
        }
        stage('Build & Test') {
            steps {
                bat 'mvn clean package'
                bat 'mvn test'
            }
        }
        stage('Build Docker Image') {
            steps { bat 'docker build -t dishagupta/bharatbazaar .' }
        }
        stage('Deploy Container') {
            steps {
                bat '''
                docker stop bharatbazaar || echo "Not running"
                docker rm bharatbazaar || echo "No container"
                docker run -d -p 8081:8080 --name bharatbazaar dishagupta/bharatbazaar
                '''
            }
        }
    }
}
