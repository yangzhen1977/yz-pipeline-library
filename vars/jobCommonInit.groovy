
import com.cloudbees.groovy.cps.NonCPS
import jenkins.model.ArtifactManager
import jenkins.util.VirtualFile
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
//import org.jenkinsci.plugins.ghprb.GhprbTrigger
import java.nio.file.StandardCopyOption
import java.util.regex.Matcher
import java.nio.file.Files
import java.util.regex.Pattern


def call(Map step_params = [:], Closure body){
    def job_params=step_params.get("job_params", parameters([]))
    def block_build = [
        $class: 'BuildBlockerProperty',
        useBuildBlocker: true,
        blockLevel: "NODE",
        scanQueueFor: "DISABLED",
        blockingJobs: ".*",
    ]

    def property_ls = [
        block_build,
        buildDiscarder(
            logRotator(
                daysToKeepStr: '5',
                numToKeepStr: '',
                artifactDaysToKeepStr: '7',
                artifactNumToKeepStr: '16',
            )
        ),
        [
            $class: 'EnvInjectJobProperty',
            info:[
                propertiesFilePath: '$ATG_job_propfile',
                loadFilesFromMaster: true,
            ],
            on: true,
            keepJenkinsSystemVariables: true,
            keepBuildVariables: true,
            overrideBuildParameters: false,

        ],
        job_params,
    ]

    properties(property_ls)
}
