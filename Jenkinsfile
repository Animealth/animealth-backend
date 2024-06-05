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
                    withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_YEOMYALOO', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        try {
                            sh '''docker login -u yeom456@gmail.com -p ${DOCKER_HUB_PASSWORD}'''
                            echo '${DOCKER_HUB_USERNAME}'
                            sh 'cd /var/jenkins_home/workspace/Animealth_animealth-backend_main/build/libs'

                            sh"""
                            docker build --tag yeomhwiju/animealth-backend:latest .
                            docker image push yeomhwiju/animealth-backend:latest
                            """
                            
                            env.dockerBuildResult = true
                        } catch(error) {
                            echo "Docker Build 단계에서 오류 발생: ${error}"
                            env.dockerBuildResult = false
                            currentBuild.result = 'FAILURE'
                        }
                    }
                }
            }
        }
        
    }
}
