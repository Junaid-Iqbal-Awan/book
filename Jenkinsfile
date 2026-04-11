pipeline {
  agent any

  environment {
    COMPOSE_FILE = "docker-compose.part2.yml"
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
  }

  post {
    always {
      sh 'docker-compose -f ${COMPOSE_FILE} ps || true'
    }
  }
}
