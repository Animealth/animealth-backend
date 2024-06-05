def PROJECT_NAME = 'animealth-backend'
pipeline{
    agent any
    stages{
        stage('project build') {
            steps{
                script{
                    sh 'cd /var/jenkins_home/workspace/Animealth_animealth-backend_main'
                    echo "gradle build 단계"
                    sh 'chmod +x ./gradlew'
                    sh './gradlew clean'
                    sh './gradlew bootJar'
                }
            }
        }
        stage('Replace YML') {
            steps {
                script {
                    echo "Replace YML 단계 실행 중"
                    withCredentials([file(credentialsId:'PROD_YML', variable:'prod_yml')]) {
                        sh "cp ${prod_yml} /var/jenkins_home/workspace/Animealth_animealth-backend_main/src/main/resources/application.yml"
                    }
                }
            }
        }
        stage('Creating a Docker image and pushing it to Docker Hub') {
            steps {
                script {
                    echo "도커 이미지 만든 후 도커 허브에 올리기"
                    withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_YEOMYALOO', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        try {
                            sh '''docker login -u yeom456@gmail.com -p ${DOCKER_HUB_PASSWORD}'''
                            echo '${DOCKER_HUB_USERNAME}'
                            sh 'cd /var/jenkins_home/workspace/Animealth_animealth-backend_main/build/libs'
                            def dockerfilePath = "/var/jenkins_home/workspace/Animealth_animealth-backend_main/Dockerfile"
                            
                            // Check if Dockerfile already exists
                            if (fileExists(dockerfilePath)) {
                                // If Dockerfile exists, delete it
                                sh "rm ${dockerfilePath}"
                                echo "Deleted existing Dockerfile."
                            }
                            
                            sh '''
                            cat > Dockerfile <<'EOF'
                            FROM openjdk:17
                            COPY build/libs/*.jar animealth.jar
                            EXPOSE 8080
                            CMD ["java", "-jar", "animealth.jar"]
                            '''

                            sh 'docker build --tag yeomhwiju/animealth:latest .'
                            sh 'docker push yeomhwiju/animealth:latest'

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
