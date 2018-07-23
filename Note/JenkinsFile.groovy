pipeline {
    agent any

    stages {
        stage('Build Note') {
            steps {
                sh "cd Note && mvn clean package spring-boot:repackage -Dmaven.test.skip=true -P prod"
            }
        }
        stage('Deploy Note') {
            steps {
                sh "cp Note/target/Note-1.0-SNAPSHOT.jar /wrj/jar/Note.jar"
            }
        }
    }
}