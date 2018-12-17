void setBuildStatus(String message, String state) {
  step([
      $class: "GitHubCommitStatusSetter",
      reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/JA2018KATMM/Battleships"],
      contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
      errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
      statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
  ]);
}

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
            steps {
                sh 'scp server/target/server-0.1.war root@51.38.130.222:/opt/tomcat/webapps'
                sh 'scp client/shade/client.jar root@51.38.130.222:/opt/tomcat/webapps/ROOT'
            }
        }
    }
    post {
        success {
            setBuildStatus("Build succeeded", "SUCCESS");
        }
        failure {
            setBuildStatus("Build failed", "FAILURE");
        }
    }
}
