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

        stage('docker compose build') {
            steps {
                script {
                    echo "docker compose build 단계 실행 중"
                    def dockerComposefilePath = "/var/jenkins_home/workspace/Animealth_animealth-backend_main/docker-compose.yml"
                        if (fileExists(dockerComposefilePath)) {
                            // If Dockerfile exists, delete it
                            sh "rm ${dockerComposefilePath}"
                            echo "Deleted existing docker compose."
                        }
                            sh '''
                            cat > docker-compose.yml <<'EOF'
                            version: "3"
                            services:
                              database:
                                image: mysql:latest
                                container_name: animealth-mysql
                                environment:
                                  - MYSQL_DATABASE= animealth-database
                                  - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}
                                ports:
                                  - 3306:3306
                                networks:
                                  - animealth_network01
                                restart: always
                            
                              application:
                                build:
                                  context: /var/jenkins_home/workspace/Animealth_animealth-backend_main/Dockerfile
                                  dockerfile: Dockerfile
                                ports:
                                  - 8080:8080
                                networks:
                                  - animealth_network01
                                depends_on:
                                  - database
                                container_name: animealth
                                restart: always
                                environment:
                                  - SPRING_DATASOURCE_URL=jdbc:mysql://animealth-mysql:3306/animealth-databae?useSSL=false&allowPublicKeyRetrieval=true
                                  - SPRING_DATASOURCE_USERNAME=root
                                  - SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
                                labels:
                                  - "com.centurylinklabs.watchtower.enable=true"
                            '''
                    sh 'docker-compose up'
                }
            }
        }
    }
}
