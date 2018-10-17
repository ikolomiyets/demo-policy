version = ""
promoted = false
projectName = ""
repository = "ikolomiyets/demo-policy"
tag = "latest"
namespace = "demo"
image = ""

podTemplate(label: 'jpod', cloud: 'OpenShift', serviceAccount: 'jenkins',
  containers: [
    containerTemplate(name: 'java', image: 'openjdk', ttyEnabled: true, command: 'cat'),
    containerTemplate(name: 'docker', image: 'docker:dind', ttyEnabled: true, command: 'cat', privileged: true,
        envVars: [secretEnvVar(key: 'DOCKER_USERNAME', secretName: 'ikolomiyets-docker-hub-credentials', secretKey: 'username'),
    ]),
    containerTemplate(name: 'kubectl', image: 'roffe/kubectl', ttyEnabled: true, command: 'cat'),
    containerTemplate(name: 'sonarqube', image: 'iktech/sonarqube-scanner', ttyEnabled: true, command: 'cat'),
  ],
  volumes: [
    secretVolume(mountPath: '/etc/.ssh', secretName: 'ssh-home'),
    secretVolume(secretName: 'ikolomiyets-docker-hub-credentials', mountPath: '/etc/.secret'),
    hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')
  ]
) {
    node('jpod') {
        stage('Prepare') {
            checkout scm
            props = readProperties file: 'gradle.properties'
            version = props['baseVersion']
            projectName = props['projectName']
            image="${repository}:${version}.${env.BUILD_NUMBER}"

            // Set up private key to access BitBucket
            sh "cat /etc/.ssh/id_rsa > ~/.ssh/id_rsa"
            sh "chmod 400 ~/.ssh/id_rsa"
        }

        stage('Build Java Code') {
            container('java') {
                try {
                    sh './gradlew clean build'
                } catch (error) {
                    step([$class: 'Mailer',
                        notifyEveryUnstableBuild: true,
                        recipients: emailextrecipients([[$class: 'CulpritsRecipientProvider'],
                                                        [$class: 'DevelopersRecipientProvider']]),
                        sendToIndividuals: true])
                    throw error
                } finally {
//                    step([$class: 'JUnitResultArchiver', testResults: 'build/test-results/**/*.xml'])
//                    step([$class: 'JacocoPublisher'])
                }
            }
        }

//        stage('SonarQube Analysis') {
//            container('sonarqube') {
//            	lock(resource: "${projectName}-sonarqube") {
//            		stage('SonarQube Analysis') {
//            	        try {
//            			    def scannerHome = tool 'sonarqube-scanner';
//            				withSonarQubeEnv('Sonarqube') {
//            			        sh "${scannerHome}/bin/sonar-scanner"
//            			    }
//            	        } catch (error) {
//                            step([$class: 'Mailer',
//                                notifyEveryUnstableBuild: true,
//                                recipients: emailextrecipients([[$class: 'CulpritsRecipientProvider'],
//                                                                [$class: 'RequesterRecipientProvider']]),
//                                sendToIndividuals: true])
//            	            throw error
//            	        }
//            		}
//            	}
//            }
//        }

//        stage("Quality Gate") {
//	          milestone(1)
//	          lock(resource: "${projectName}-sonarqube") {
//                  timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
//                    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
//                    if (qg.status != 'OK') {
//                        step([$class: 'Mailer',
//                            notifyEveryUnstableBuild: true,
//                            recipients: emailextrecipients([[$class: 'CulpritsRecipientProvider'],
//                                                            [$class: 'RequesterRecipientProvider']]),
//                            sendToIndividuals: true])
//                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
//                    }
//                  }
//		          milestone(2)
//             }
//        }

        stage('Build Docker Image') {
            container('docker') {
                sh "docker build --build-arg VERSION=${version}.${env.BUILD_NUMBER} -t ${image} ."
                sh 'cat /etc/.secret/password | docker login --password-stdin --username $DOCKER_USERNAME'
                sh "docker push ${image}"
                sh "docker tag ${image} ${repository}:${tag}"
                sh "docker push ${repository}:${tag}"
                milestone(3)
            }
        }

        stage('Tag Source Code') {
            def repositoryCommitterEmail = "jenkins@iktech.io"
            def repositoryCommitterUsername = "jenkinsCI"
            values = version.tokenize(".")

            sh "git config user.email ${repositoryCommitterEmail}"
            sh "git config user.name '${repositoryCommitterUsername}'"
            sh "git tag -d v${values[0]} || true"
            sh "git push origin :refs/tags/v${values[0]}"
            sh "git tag -d v${values[0]}.${values[1]} || true"
            sh "git push origin :refs/tags/v${values[0]}.${values[1]}"
            sh "git tag -d v${version} || true"
            sh "git push origin :refs/tags/v${version}"

            sh "git tag -fa v${values[0]} -m \"passed CI\""
            sh "git tag -fa v${values[0]}.${values[1]} -m \"passed CI\""
            sh "git tag -fa v${version} -m \"passed CI\""
            sh "git tag -a v${version}.${env.BUILD_NUMBER} -m \"passed CI\""
            sh "git push -f --tags"

            milestone(4)
        }

        stage('Deploy Latest') {
            container('kubectl') {
                sh "kubectl patch -n ${namespace} deployment ${projectName} -p '{\"spec\": { \"template\" : {\"spec\" : {\"containers\" : [{ \"name\" : \"${projectName}\", \"image\" : \"${image}\"}]}}}}'"
                milestone(5)
            }
        }
    }
}


properties([[
    $class: 'BuildDiscarderProperty',
    strategy: [
        $class: 'LogRotator',
        artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10']
    ]
]);
