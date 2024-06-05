def PROJECT_NAME = 'animealth-backend'
pipeline{
    agent any
    stages{
        stage('Prepare'){
            steps {
                echo "여기 준비"
                sh 'cd /var/jenkins_home/workspace/Animealth_animealth-backend_main'
                sh 'chmod +x ./gradlew'
                sh 'gradle clean'
                sh 'pwd'
            }
        }
        stage('Build') {
            steps {
                echo "여기 빌드"
                sh 'gradlew build -x test'
                sh 'pwd'

            }
        }
        stage('Test') {
            steps {
                echo "여기 테스트"
                sh 'gradlew test'
                sh 'pwd'
            }
        }
        stage('Deploy Prepare'){
            steps{
                echo "여기 배포 준비"
                sh 'sudo kill $(pgrep -f ${PROJECT_NAME})'
                sh 'pwd'
            }
        }
        stage('Deploy') {
            steps {
                echo "여기 배포"
                sh 'nohup java -jar ./build/libs/${PROJECT_NAME}.jar &'
                sh 'pwd'
            }
        }
    }
}
