pipeline {
    agent any
    
    environment {
        DEPLOY_PATH = '.'
        RECIPIENT_EMAIL = 'haseebahmad8986@gmail.com'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from GitHub...'
                checkout scm
            }
        }
        
        stage('Deploy Locally') {
            steps {
                script {
                    echo 'Deploying on Jenkins EC2 instance (localhost)...'
                    
                    sh '''
                        docker compose down || true
                        docker compose build --no-cache
                        docker compose up -d
                        echo "Deployment completed successfully!"
                        echo "Waiting for services to start..."
                        sleep 30
                    '''
                }
            }
        }
        
        stage('Verify Deployment') {
            steps {
                script {
                    echo 'Verifying deployment...'
                    
                    sh '''
                        docker compose ps
                        echo "Waiting additional time for services..."
                        sleep 15
                    '''
                }
            }
        }
        
        stage('Run Selenium Tests') {
            steps {
                script {
                    echo 'Running Selenium automated tests...'
                    
                    sh '''
                        docker run --rm \
                            --network host \
                            -v ${WORKSPACE}/SeleniumTests:/app \
                            -w /app \
                            markhobson/maven-chrome:latest \
                            mvn clean test -Dbase.url=http://localhost:80
                    '''
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'SeleniumTests/target/surefire-reports/*.xml'
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'SeleniumTests/target/surefire-reports/**/*'
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline executed successfully!'
            echo 'Application deployed and all tests passed!'
            
            mail to: "${env.RECIPIENT_EMAIL}",
                 subject: "SUCCESS: Jenkins Build ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """Build Successful!
                 
Job: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Status: SUCCESS

All Selenium tests passed successfully.

View Build: ${env.BUILD_URL}"""
        }
        failure {
            echo 'Pipeline failed. Check the logs for details.'
            
            mail to: "${env.RECIPIENT_EMAIL}",
                 subject: "FAILED: Jenkins Build ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """Build Failed!
                 
Job: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Status: FAILURE

Please check the test results and logs for details.

View Build: ${env.BUILD_URL}"""
        }
        always {
            echo 'Pipeline completed.'
        }
    }
}