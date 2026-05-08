pipeline {
  agent any

  environment {
    COMPOSE_FILE = "docker-compose.part2.yml"
    TEST_IMAGE = "markhobson/maven-chrome:latest"
    APP_BASE_URL = "http://localhost:5001"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Prepare Env') {
      steps {
        sh 'test -f .env || cp .env.example .env'
      }
    }

    stage('Prepare Images') {
      steps {
        script {
          docker.image('node:20-alpine').pull()
          docker.image('mongo:7').pull()
          docker.image(env.TEST_IMAGE).pull()
        }
      }
    }

    stage('Deploy Part II Stack') {
      steps {
        sh 'docker-compose -f ${COMPOSE_FILE} down || true'
        sh 'docker-compose -f ${COMPOSE_FILE} up -d'
      }
    }

    stage('Health Check') {
      steps {
        sh 'docker-compose -f ${COMPOSE_FILE} ps'
      }
    }

    stage('Selenium Tests') {
      steps {
        sh 'docker run --rm --network host --memory=800m --memory-swap=2g -e APP_BASE_URL=${APP_BASE_URL} -v $WORKSPACE/SeleniumTests:/workspace -w /workspace ${TEST_IMAGE} mvn test'
      }
      
    }
  }

  post {
  always {
    sh 'docker-compose -f ${COMPOSE_FILE} ps || true'
  }
  success {
    script {
      def pusherEmail = sh(
        script: "git log -1 --pretty=format:'%ae'",
        returnStdout: true
      ).trim()
      emailext(
        to: "${pusherEmail}",
        subject: "✅ Selenium tests passed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: """
          <h2>Build Succeeded</h2>
          <p>All Selenium tests passed for <b>${env.JOB_NAME}</b> #${env.BUILD_NUMBER}.</p>
          <p><a href="${env.BUILD_URL}">View in Jenkins</a></p>
        """,
        mimeType: 'text/html',
        attachmentsPattern: 'SeleniumTests/target/surefire-reports/*.xml'
      )
    }
  }
  failure {
    script {
      def pusherEmail = sh(
        script: "git log -1 --pretty=format:'%ae'",
        returnStdout: true
      ).trim()
      emailext(
        to: "${pusherEmail}",
        subject: "❌ Selenium tests failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: """
          <h2>Build Failed</h2>
          <p>Selenium tests failed for <b>${env.JOB_NAME}</b> #${env.BUILD_NUMBER}.</p>
          <p><a href="${env.BUILD_URL}">View console output</a></p>
        """,
        mimeType: 'text/html',
        attachmentsPattern: 'SeleniumTests/target/surefire-reports/*.xml'
      )
    }
  }
}
}
