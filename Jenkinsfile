#!/usr/bin/env groovy

node {
    try {
        notifyBuild('STARTED')

        stage('checkout') {
            checkout scm
        }
        stage('check java') {
            sh "java -version"
        }

        stage('clean') {
            sh "chmod +x mvnw"
            sh "./mvnw clean"
        }

        stage('backend tests') {
            try {
                sh "./mvnw test"
            } catch(err) {
                throw err
            } finally {
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }

        stage('packaging') {
            sh "./mvnw package -DskipTests dockerfile:build"
        }

        stage('deploy') {
            try {
                sh "docker stop library-middleware"
                sh "docker rm library-middleware"
            } catch (err) {
                echo "no such container"
            }
            sh "docker run --restart=always -d -p 8080:8080 --name library-middleware library-middleware"
        }
    } catch (e) {
      currentBuild.result = "FAILED"
      throw e
    } finally {
      notifyBuild(currentBuild.result)
    }
}

def notifyBuild(String buildStatus = 'STARTED') {
    buildStatus =  buildStatus ?: 'SUCCESSFUL'

    def colorName = 'RED'
    def colorCode = '#FF0000'
    def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
    def summary = "${subject} (${env.BUILD_URL})"

    if (buildStatus == 'STARTED') {
      color = 'YELLOW'
      colorCode = '#FFFF00'
    } else if (buildStatus == 'SUCCESSFUL') {
      color = 'GREEN'
      colorCode = '#00FF00'
    } else {
      color = 'RED'
      colorCode = '#FF0000'
    }

    slackSend (color: colorCode, message: summary)
}
