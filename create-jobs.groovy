import jenkins.model.*
import javaposse.jobdsl.plugin.ExecuteDslScripts


def workspace = new File("/var/jenkins_home/casc/jobs")
def files = workspace.listFiles().findAll { 
    it.name.endsWith(".groovy")
}

files.each { file ->
    def script = new ExecuteDslScripts()
    script.setTargets(file.getAbsolutePath())
    script.setIgnoreExisting(false)
    script.setRemovedJobAction(ExecuteDslScripts.RemovedJobAction.IGNORE)
    script.setRemovedViewAction(ExecuteDslScripts.RemovedViewAction.IGNORE)
    script.setLookupStrategy(javaposse.jobdsl.plugin.LookupStrategy.SEED_JOB)
    script.setAdditionalClasspath('')
    script.perform(Jenkins.instance, Jenkins.instance.getDescriptorByType(ExecuteDslScripts.DescriptorImpl))
}