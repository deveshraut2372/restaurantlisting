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
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://15.237.126.131:9000/ -Dsonar.login=squ_517249cec8a2cea3fd52c77614f138f2f8813525'
            }
        }

        stage('Check code coverage')
        {
            steps{
                script
                {

                def token = "squ_517249cec8a2cea3fd52c77614f138f2f8813525"
                def sonarQubeUrl = "http://15.237.126.131:9000/api"
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
        steps {

            checkout([
                $class: 'GitSCM',
                branches: [[name: '*/master']],
                userRemoteConfigs: [[
                    credentialsId: 'github-ssh-key',
                    url: 'git@github.com:deveshraut2372/deployement-files.git'
                ]]
            ])

            script {

                sh """
                    echo "Before change:"
                    cat aws/restaurant-menifest.yml

                    sed -i 's|image: namrata11111/restaurant-listing-service:.*|image: namrata11111/restaurant-listing-service:${VERSION}|' aws/restaurant-menifest.yml

                    echo "After change:"
                    cat aws/restaurant-menifest.yml
                """


                sh """
                    git config user.email "jenkins@example.com"
                    git config user.name "jenkins"

                    git status

                    git add aws/restaurant-menifest.yml

                    git commit -m "Update image tag ${VERSION}" || echo "No changes"

                    git push origin HEAD:master
                """
            }
        }
    }


//stage('Update Image Tag In GitOps'){
//    steps {
//
//        deleteDir()
//
//        checkout scmGit(
//            branches:[[name:'*/master']],
//            extensions:[],
//            userRemoteConfigs:[[
//                credentialsId:'github-ssh-key',
//                url:'git@github.com:deveshraut2372/deployement-files.git'
//            ]]
//        )
//
//        script {
//
//            sh "cat aws/restaurant-menifest.yml"
//
//            sh """
//            sed -i 's|image:.*restaurant-listing-service:.*|image: namrata11111/restaurant-listing-service:${VERSION}|' aws/restaurant-menifest.yml
//            """
//
//            sh "cat aws/restaurant-menifest.yml"
//
//            sh "git add aws/restaurant-menifest.yml"
//
//            sh "git commit -m 'Update image tag ${VERSION}'"
//
//            sshagent(['github-ssh-key']) {
//                sh "git push origin master"
//            }
//        }
//    }
//}


////////////////


//    stage('Update Image Tag In GitOps'){
//        steps{
//            checkout scmGit(branches :[[name:'*/master']], extensions:[],userRemoteConfigs:[[ credentialsId: 'github-ssh-key' , url: 'git@github.com:deveshraut2372/deployement-files.git']])
//            script{
////                sh '''
////                    sed -i "s/image: namrata11111\\/restaurant-listing-service:${VERSION}/" aws/restaurant-menifest.yml
////                '''
//                sh """
//                sed -i 's|image: namrata11111/restaurant-listing-service:.*|image: namrata11111/restaurant-listing-service:${VERSION}|' aws/restaurant-menifest.yml
//                """
//
//                sh 'git checkout master '
//                sh 'git add .'
////                sh 'git commit -m "Update image tag "'
//                sh 'git commit -m "Update image tag ${VERSION}" || true'
//
//              sshagent(['github-ssh-key'])
//                {
//                    sh('git push origin master')
//                }
//            }
//        }
//    }


//    stage('Update Image Tag In GitOps') {
//        steps {
//            checkout scmGit(
//                branches: [[name: '*/master']],
//                extensions: [],
//                userRemoteConfigs: [[
//                    credentialsId: 'github-ssh-key',
//                    url: 'git@github.com:deveshraut2372/deployement-files.git'
//                ]]
//            )
//
//            script {
//                sh """
//                sed -i 's|image: namrata11111/restaurant-listing-service:.*|image: namrata11111/restaurant-listing-service:${VERSION}|' aws/restaurant-menifest.yml
//                """
//
//                sh 'git add aws/restaurant-menifest.yml'
//                sh 'git commit -m "Update image tag to ${VERSION}" || true'
//
//                sshagent(['github-ssh-key']) {
//                    sh 'git push origin master'
//                }
//            }
//        }
//    }


    }
}