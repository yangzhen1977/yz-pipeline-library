#!/usr/bin/groovy
package org.yz

import com.cloudbees.groovy.cps.NonCPS
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob

def getResourceName() {
    def name = env.JOB_NAME

    // only lower case alphanumeric. "-" and "." are allowed for Kubernetes name with max lenght of 253
    name = name.replaceAll("/", "-").toLowerCase()
    name = name.replaceAll(/[^a-z0-9\.-]/, "").take(254)

    return name
}