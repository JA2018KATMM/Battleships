#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
	    stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean install'
            }
        }
    	stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                sh 'scp server/target/server-0.1.war root@51.38.130.222:/opt/tomcat/webapps'
                sh 'scp client/shade/client.jar root@51.38.130.222:/opt/tomcat/webapps/ROOT'
            }
        }
    }
}