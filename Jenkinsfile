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
                    withCredentials([string(credentialsId:'DOCKER_HUB_YEOMYALOO', variable:'docker_hub_password')]) {
                        try {
                            sh '''docker login -u yeom456@github.com -p ${docker_hub_password}'''
                            
                            sh '''
                            cat > Dockerfile <<'EOF'
                            FROM openjdk:17
                            ARG BUILD_PATH=/var/jenkins_home/workspace/Animealth_animealth-backend_main/build/libs/*.jar
                            COPY $BUILD_PATH ./animealth-backend.jar
                            EXPOSE 8080
                            ENTRYPOINT ["java", "-jar", "/animealth-backend.jar"]

                            '''

                            sh"""
                            docker buildx use multiarch-builder
                            docker buildx build --platform linux/arm64,linux/amd64 --tag yeomhwiju/animealth-backend:latest --push .
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
