pipeline {
    agent any

    environment {
        IMAGE = 'ccm-generator'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Source checked out from SCM'
            }
        }

        stage('Build & Test') {
            steps {
                // The multi-stage Dockerfile runs `mvn clean package` inside it,
                // which compiles the code AND runs the unit tests. If a test fails,
                // the image build fails and the pipeline stops right here.
                sh 'docker build -t $IMAGE:$BUILD_NUMBER .'
            }
        }

        stage('Deploy to Dev') {
            steps {
                echo 'Deploying to DEV environment'
                sh 'docker tag $IMAGE:$BUILD_NUMBER $IMAGE:dev'
                sh 'docker run --rm $IMAGE:dev'
            }
        }

        stage('Approval') {
            steps {
                input message: 'Promote this build to PRODUCTION?', ok: 'Promote'
            }
        }

        stage('Promote to Prod') {
            steps {
                echo 'Promoting to PROD environment'
                sh 'docker tag $IMAGE:$BUILD_NUMBER $IMAGE:prod'
                echo 'Current build is now tagged as prod'
            }
        }
    }

    post {
        success { echo 'Pipeline completed successfully.' }
        failure { echo 'Pipeline failed — check the stage logs above.' }
    }
}