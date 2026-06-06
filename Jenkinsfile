pipeline {
    agent any
    tools {
        jdk 'JDK-17'
    }

    stages {
        stage('1. Checkout') {
            steps {
                git branch: 'main', credentialsId: 'github-token', url: 'https://github.com/larvaaa/shop-springboot-backend.git'
            }
        }


        // ==========================================
        // [eureka 서버] 파이프라인
        // ==========================================
        stage('Eureka Server') {
            // eureka-server 폴더 하위의 코드가 변경되었을 때만 실행!
            when { changeset "eureka-server/**" }
            steps {
                script { env.JENKINS_NODE_COOKIE = 'dontKillMe' }
                echo "======================================"
                echo "[ Eureka Server 빌드 ]"
                echo "======================================"

                // Gradle 멀티 모듈 특정 서비스만 빌드
                sh 'chmod +x gradlew'
                sh './gradlew :eureka-server:clean :eureka-server:build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx256m"'

                // 기존 주문 서비스 포트(8761) 종료 및 새 Jar 실행
                sh '''
                    PID=$(lsof -t -i:8761 || true)
                    if [ -n "$PID" ]; then kill -9 $PID; sleep 3; fi
                    nohup java -jar eureka-server/build/libs/*SNAPSHOT.jar > eureka.log 2>&1 &
                '''
            }
        }


        // ==========================================
        // [게이트웨이 서비스] 파이프라인
        // ==========================================
        stage('Apigateway Service') {
            // apigateway-service 폴더 하위의 코드가 변경되었을 때만 실행!
            when { changeset "apigateway-service/**" }
            steps {
                script { env.JENKINS_NODE_COOKIE = 'dontKillMe' }
                echo "======================================"
                echo "[ Apigateway Service 빌드 ]"
                echo "======================================"

                // Gradle 멀티 모듈 특정 서비스만 빌드
                sh 'chmod +x gradlew'
                sh './gradlew :apigateway-service:clean :apigateway-service:build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx256m"'

                // 기존 주문 서비스 포트(8000) 종료 및 새 Jar 실행
                sh '''
                    PID=$(lsof -t -i:8000 || true)
                    if [ -n "$PID" ]; then kill -9 $PID; sleep 3; fi
                    nohup java -jar apigateway-service/build/libs/*SNAPSHOT.jar > apigateway.log 2>&1 &
                '''
            }
        }

        // ==========================================
        // [관리자 서비스] 파이프라인
        // ==========================================
        stage('Admin Service') {
            // admin-service 폴더 하위의 코드가 변경되었을 때만 실행!
            when { changeset "admin-service/**" }
            steps {
                script { env.JENKINS_NODE_COOKIE = 'dontKillMe' }
                echo "======================================"
                echo "[ Admin Service 빌드 ]"
                echo "======================================"

                // Gradle 멀티 모듈 특정 서비스만 빌드
                sh 'chmod +x gradlew'
                sh './gradlew :admin-service:clean :admin-service:build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx256m"'

                // 기존 주문 서비스 포트(8081) 종료 및 새 Jar 실행
                sh '''
                    PID=$(lsof -t -i:8081 || true)
                    if [ -n "$PID" ]; then kill -9 $PID; sleep 3; fi
                    nohup java -jar -Dspring.profiles.active=dev admin-service/build/libs/*SNAPSHOT.jar > admin.log 2>&1 &
                '''
            }
        }

        // ==========================================
        // [회원 서비스] 파이프라인
        // ==========================================
        stage('Member Service') {
            // member-service 폴더 하위의 코드가 변경되었을 때만 실행!
            when { changeset "member-service/**" }
            steps {
                script { env.JENKINS_NODE_COOKIE = 'dontKillMe' }
                echo "======================================"
                echo "[ Member Service 빌드 ]"
                echo "======================================"

                sh 'chmod +x gradlew'
                sh './gradlew :member-service:clean :member-service:build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx256m"'

                sh '''
                    PID=$(lsof -t -i:8082 || true)
                    if [ -n "$PID" ]; then kill -9 $PID; sleep 3; fi
                    nohup java -jar -Dspring.profiles.active=dev member-service/build/libs/*SNAPSHOT.jar > member.log 2>&1 &
                '''
            }
        }

        // ==========================================
        // [가게 서비스] 파이프라인
        // ==========================================
        stage('Store Service') {
            // store-service 폴더 하위의 코드가 변경되었을 때만 실행!
            when { changeset "store-service/**" }
            steps {
                script { env.JENKINS_NODE_COOKIE = 'dontKillMe' }
                echo "======================================"
                echo "[ Store Service 빌드 ]"
                echo "======================================"

                sh 'chmod +x gradlew'
                sh './gradlew :store-service:clean :store-service:build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx256m"'

                sh '''
                    PID=$(lsof -t -i:8083 || true)
                    if [ -n "$PID" ]; then kill -9 $PID; sleep 3; fi
                    nohup java -jar -Dspring.profiles.active=dev store-service/build/libs/*SNAPSHOT.jar > store.log 2>&1 &
                '''
            }
        }


    }
}