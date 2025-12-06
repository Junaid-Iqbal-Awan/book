pipeline {
    agent any
    
    environment {
        // Deploy on the same EC2 where Jenkins is running (using Jenkins workspace)
        DEPLOY_PATH = '.'  // Use current workspace directory
        // Email configuration for test results
        RECIPIENT_EMAIL = "${env.GIT_COMMITTER_EMAIL ?: 'haseebahmad8986@gmail.com'}"
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
                    
                    // Deploy locally on the same machine where Jenkins runs
                    sh """
                        docker compose down || true
                        docker compose build --no-cache
                        docker compose up -d
                        echo "Deployment completed successfully!"
                        
                        // Wait for services to be healthy
                        sleep 30
                    """
                }
            }
        }
        
        stage('Verify Deployment') {
            steps {
                script {
                    echo 'Verifying deployment...'
                    
                    sh """
                        docker compose ps
                        
                        // Check if frontend is responding
                        curl -f http://localhost:80 || echo "Waiting for frontend..."
                        sleep 10
                        curl -f http://localhost:80 || exit 1
                    """
                }
            }
        }
        
        stage('Run Selenium Tests') {
            steps {
                script {
                    echo 'Running Selenium automated tests...'
                    
                    // Run tests in markhobson/maven-chrome Docker container
                    sh """
                        docker run --rm \\
                            --network host \\
                            -v \${WORKSPACE}/SeleniumTests:/app \\
                            -w /app \\
                            markhobson/maven-chrome:latest \\
                            mvn clean test -Dbase.url=http://localhost:80
                    """
                }
            }
            post {
                always {
                    // Archive test results
                    junit allowEmptyResults: true, testResults: 'SeleniumTests/target/surefire-reports/*.xml'
                    
                    // Archive test reports
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'SeleniumTests/target/surefire-reports/**/*'
                }
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline executed successfully!'
            echo 'Application deployed and all tests passed!'
            
            // Send success email
            emailext(
                subject: "✅ Jenkins Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <h2>Build Successful!</h2>
                    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                    <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                    <p><strong>Status:</strong> SUCCESS</p>
                    <p><strong>Application URL:</strong> <a href="http://ec2-3-85-243-204.compute-1.amazonaws.com">http://ec2-3-85-243-204.compute-1.amazonaws.com</a></p>
                    <p>All Selenium tests passed successfully.</p>
                    <p><a href="${env.BUILD_URL}">View Build Details</a></p>
                """,
                mimeType: 'text/html',
                to: "${env.RECIPIENT_EMAIL}",
                recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
            )
        }
        failure {
            echo '❌ Pipeline failed. Check the logs for details.'
            
            // Send failure email
            emailext(
                subject: "❌ Jenkins Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <h2>Build Failed!</h2>
                    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                    <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                    <p><strong>Status:</strong> FAILURE</p>
                    <p>Please check the test results and logs for details.</p>
                    <p><a href="${env.BUILD_URL}">View Build Details</a></p>
                    <p><a href="${env.BUILD_URL}testReport/">View Test Report</a></p>
                """,
                mimeType: 'text/html',
                to: "${env.RECIPIENT_EMAIL}",
                recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
            )
        }
        always {
            echo 'Pipeline completed.'
        }
    }
}