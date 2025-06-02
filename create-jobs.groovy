import jenkins.model.*
import hudson.model.*
import java.nio.file.Files
import java.nio.file.Paths

def xmlStream = new FileInputStream('/tmp/jobs/sample-node-service.xml')
def job = Jenkins.instance.createProjectFromXML('sample-nodejs-service', xmlStream)