#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                sh 'mvn -B clean test'
            }
        }
	    stage('Build') {
            steps {
                sh 'mvn -B -DskipTests install'
            }
        }
        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                sh 'scp server/target/server.war root@51.38.130.222:/opt/tomcat/webapps'
                sh 'scp client/shade/client.jar root@51.38.130.222:/opt/tomcat/webapps/ROOT'
            }
        }
    }
}