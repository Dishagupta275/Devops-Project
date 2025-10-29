pipeline {
    agent any

    tools {
        jdk 'jdk17'      // Uses JDK 17 (configured in Jenkins Global Tool)
        maven 'maven'    // Uses Maven (configured in Jenkins Global Tool)
    }

    environment {
        IMAGE_NAME = 'dishagupta/bharatbazaar'
        SONAR_HOST_URL = 'http://localhost:9000'
    }

    stages {

        // 🔹 1️⃣ Start SonarQube inside Docker
        stage('Start SonarQube') {
            steps {
                echo 'Starting SonarQube container...'
                bat 'docker stop sonarqube || echo "SonarQube not running"'
                bat 'docker rm sonarqube || echo "Old container removed"'
                bat 'docker run -d -p 9000:9000 --name sonarqube -v sonarqube_data:/opt/sonarqube/data sonarqube:lts'
                echo 'Waiting for SonarQube to start...'
                bat 'powershell -Command "Start-Sleep -Seconds 60"'
            }
        }

        // 🔹 2️⃣ Clone your GitHub repository
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/Dishagupta275/Devops-Project.git'
            }
        }

        // 🔹 3️⃣ Build & Test the Java Application
        stage('Build & Test') {
            steps {
                bat 'mvn clean package'
                bat 'mvn test'
            }
        }

        // 🔹 4️⃣ Run SonarQube Code Quality Analysis
        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                    bat '''
                    echo Running SonarQube analysis...
                    mvn sonar:sonar ^
                      -Dsonar.projectKey=bharatbazaar ^
                      -Dsonar.host.url=%SONAR_HOST_URL% ^
                      -Dsonar.login=%SONAR_TOKEN%
                    '''
                }
            }
        }

        // 🔹 5️⃣ Build Docker Image for the Application
        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME% .'
            }
        }

        // 🔹 6️⃣ Deploy the Application Container
        stage('Deploy Application') {
            steps {
                bat '''
                docker stop bharatbazaar || echo "Not running"
                docker rm bharatbazaar || echo "No container"
                docker run -d -p 8082:8080 --name bharatbazaar %IMAGE_NAME%
                '''
            }
        }
    }

    // 🔹 7️⃣ Cleanup after the pipeline
    post {
        always {
            echo 'Cleaning up...'
            bat 'docker stop sonarqube || echo "SonarQube already stopped"'
        }
    }
}
