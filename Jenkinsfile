pipeline{
    agent any

    environment{
        DOCKERHUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIAL')
        VERSION = "${env.BUILD_ID}"
    }

    tools{
        maven "Maven"
    }

    stages{

        stage('Maven Build'){
            steps{
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests'){
            steps{
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis'){
            steps{
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://35.180.36.214:9000/ -Dsonar.login=squ_6150850f0a81c23d57843f3ec3636df9080d70ff'
            }
        }

        stage('Check code coverage')
        {
            steps{
                script
                {

                def token = "squ_517249cec8a2cea3fd52c77614f138f2f8813525"
                def sonarQubeUrl = "http://15.237.126.131:9000/"
                def componentKey = "com.devesh:restaurant-service"
                def coverageThreshold = 80.0

                def response = sh (
                        script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                        returnStdout: true
                    ).trim()

                def coverage= sh (
                    script: "echo '${response}' | jq -r '.component.measures[0].value'",
                        returnStdout: true
                    ).trim().toDouble()

                echo "Coverage: ${coverage}"

                    if (coverage < coverageThreshold) {
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        }

       stage('Docker Build and Push') {
      steps {
          sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
          sh 'docker build -t namrata11111/restaurant-listing-service:${VERSION} .'
          sh 'docker push namrata11111/restaurant-listing-service:${VERSION}'
      }
    }

        stage('Cleanup Workspace') {
      steps {
        deleteDir()

      }
    }


    stage('Update Image Tag In GitOps'){
        steps{
            checkout scmGit(branches :[[name:'*/master']], extensions:[],userRemoteConfigs:[[ credentialsId: 'github-ssh-key' , url: 'git@github.com:deveshraut2372/deployement-files.git']])
            script{
                sh '''
                    sed -i "s/image: namrata11111\\/restaurant-listing-service:${VERSION}/" aws/restaurant-menifest.yml
                '''
                sh 'git checkout master '
                sh 'git add .'
                sh 'git commit -m "Update image tag "'

              sshagent(['git-ssh'])
                {
                    sh('git push')
                }
            }
        }
    }


    }
}