pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'cd Note && mvn clean package spring-boot:repackage -Dmaven.test.skip=true -P prod'
            }
        }
        stage('Deploy') {
            steps {
                sh 'cp Note/target/Note-1.0-SNAPSHOT.jar /wrj/jar/Note.jar'
            }
        }
        stage('Restart') {
            steps {
//                sh script: 'ps -aux | grep Note.jar | grep -v grep | awk \'{print $2}\' | xargs kill', returnStatus: true
                sh 'ps -aux | grep java | xargs echo'
                sh 'JENKINS_NODE_COOKIE=dontKillMe nohup java -jar /wrj/jar/Note.jar > /wrj/log/Note.log 2>&1 &'
            }
        }
    }
}
