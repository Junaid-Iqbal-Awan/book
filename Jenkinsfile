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
            script {
                echo 'Pipeline executed successfully!'
                echo 'Application deployed and all tests passed!'
                
                def testResultAction = currentBuild.rawBuild.getAction(hudson.tasks.junit.TestResultAction.class)
                def testSummary = "No test results found"
                def totalTests = 0
                def passedTests = 0
                def failedTests = 0
                def skippedTests = 0
                
                if (testResultAction != null) {
                    totalTests = testResultAction.getTotalCount()
                    failedTests = testResultAction.getFailCount()
                    skippedTests = testResultAction.getSkipCount()
                    passedTests = totalTests - failedTests - skippedTests
                    testSummary = """Test Results:
  Total Tests: ${totalTests}
  Passed: ${passedTests}
  Failed: ${failedTests}
  Skipped: ${skippedTests}"""
                }
                
                mail to: "${env.RECIPIENT_EMAIL}",
                     subject: "SUCCESS: Jenkins Build ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                     body: """Build Successful!

Job: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Status: SUCCESS

${testSummary}

All Selenium tests passed successfully.

View Build: ${env.BUILD_URL}
View Test Report: ${env.BUILD_URL}testReport/"""
            }
        }
        failure {
            script {
                echo 'Pipeline failed. Check the logs for details.'
                
                def testResultAction = currentBuild.rawBuild.getAction(hudson.tasks.junit.TestResultAction.class)
                def testSummary = "No test results found"
                def totalTests = 0
                def passedTests = 0
                def failedTests = 0
                def skippedTests = 0
                
                if (testResultAction != null) {
                    totalTests = testResultAction.getTotalCount()
                    failedTests = testResultAction.getFailCount()
                    skippedTests = testResultAction.getSkipCount()
                    passedTests = totalTests - failedTests - skippedTests
                    testSummary = """Test Results:
  Total Tests: ${totalTests}
  Passed: ${passedTests}
  Failed: ${failedTests}
  Skipped: ${skippedTests}"""
                }
                
                mail to: "${env.RECIPIENT_EMAIL}",
                     subject: "FAILED: Jenkins Build ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                     body: """Build Failed!

Job: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Status: FAILURE

${testSummary}

Please check the test results and logs for details.

View Build: ${env.BUILD_URL}
View Test Report: ${env.BUILD_URL}testReport/"""
            }
        }
        always {
            echo 'Pipeline completed.'
        }
    }
}