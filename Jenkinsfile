def PROJECT_NAME = 'animealth-backend'
pipeline{
    agent any
    stages{
        stage('Prepare'){
            steps {
                echo "여기 준비"
                sh 'cd /var/jenkins_home/workspace/Animealth_animealth-backend_main'
                sh 'chmod +x ./gradlew'
                sh './gradlew clean'
                sh 'pwd'
            }
        }
        stage('build') {
            steps{
                script{
                    echo "gradle build 단계"
                    sh 'chmod +x ./gradlew'
                    sh './gradlew bootJar'
                }
            }
        }
        stage('making docker container image && push on docker hub') {
            steps {
                script {
                    echo "Docker Build 단계 실행 중"
    }
}