def PROJECT_NAME = 'animealth-backend'
pipeline{
    agent any
    stages{
        stage('Prepare'){
            steps {
                sh 'chmod +x ./gradlew'
                sh 'gradle clean'
            }
        }
        stage('Build') {
            steps {
                sh 'gradlew build -x test'
            }
        }
        stage('Test') {
            steps {
                sh 'gradlew test'
            }
        }
        stage('Deploy Prepare'){
            steps{
                sh 'sudo kill $(pgrep -f ${PROJECT_NAME})'
            }
        }
        stage('Deploy') {
            steps {
                sh 'nohup java -jar ./build/libs/${PROJECT_NAME}.jar &'
            }
        }
    }
}
