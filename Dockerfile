FROM jenkins/jenkins:lts-jdk21 AS download-plugins

ARG VERSION_PLUGIN_MANAGER=2.13.2

# Download Jenkins plugin manager
RUN curl -fsSL \
    https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download/${VERSION_PLUGIN_MANAGER}/jenkins-plugin-manager-${VERSION_PLUGIN_MANAGER}.jar \
    -o /tmp/plugin-manager.jar

COPY plugins.txt plugins.txt

RUN mkdir /tmp/plugins
RUN java -jar /tmp/plugin-manager.jar \
    --war /usr/share/jenkins/jenkins.war \
    --plugin-file plugins.txt \
    --plugin-download-directory /tmp/plugins

FROM jenkins/jenkins:lts-jdk21

ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

COPY --from=download-plugins /tmp/plugins /usr/share/jenkins/ref/plugins
COPY casc/ /var/jenkins_home/casc
COPY create-jobs.groovy /var/jenkins_home/init.groovy.d/create-jobs.groovy