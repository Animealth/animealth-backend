def PROJECT_NAME = 'animealth-backend'
pipeline{
    agent any
    stages{
        stage('Prepare'){
            steps {
                sh 'chmod +x ./gradlew'
                sh 'gradle clean'
                sh "Prepare pwd"
            }
        }
        stage('Build') {
            steps {
                sh 'gradlew build -x test'
                sh "Build pwd"

            }
        }
        stage('Test') {
            steps {
                sh 'gradlew test'
                sh "Test pwd"
            }
        }
        stage('Deploy Prepare'){
            steps{
                sh 'sudo kill $(pgrep -f ${PROJECT_NAME})'
                sh "Deploy Prepare pwd"
            }
        }
        stage('Deploy') {
            steps {
                sh 'nohup java -jar ./build/libs/${PROJECT_NAME}.jar &'
                sh "Deploy pwd"
            }
        }
    }
}
