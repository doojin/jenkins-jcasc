multibranchPipelineJob('sample-nodejs-service') {
    displayName('sample-nodejs-service')
    description('multibranch pipeline: simple-nodejs-service')

    branchSources {
        github {
            id('40d0ddab-5477-40e4-8459-546067d276c0')
            scanCredentialsId('Doojin-Jenkins')
            repoOwner('doojin')
            repository('sample-nodejs-service')
            apiUri('https://api.github.com')
            traits {
                githubChecksTrait {
                    verboseConsoleLog(false)
                }
                githubStatusChecksTrait {
                    skip(true)
                    skipNotifications(false)
                    unstableBuildNeutral(false)
                    name('Jenkins')
                    suppressLogs(false)
                    skipProgressUpdates(false)
                }
                branchDiscoveryTrait {
                    strategyId(1)
                }
                originPullRequestDiscoveryTrait {
                    strategyId(1)
                }
                tagDiscoveryTrait()
                headWildcardFilter {
                    includes('main PR-* v*')
                    excludes('')
                }
            }
        }
    }

    orphanedItemStrategy {
        discardOldItems {
            numToKeep(-1)
            daysToKeep(-1)
        }
    }

    factory {
        workflowBranchProjectFactory {
            scriptPath('Jenkinsfile')
        }
    }

    buildStrategies {
        allBranchesSame {
            strategies {
                namedBranches {
                    filters {
                        wildcards {
                            includes('main PR-*')
                            excludes('')
                            caseSensitive(false)
                        }
                    }
                }
                tags {
                    atLeastMillis(-1)
                    atMostMillis(604800000)
                }
                changeRequests {
                    ignoreTargetOnlyChanges(false)
                    ignoreUntrustedChanges(false)
                }
            }
        }
    }
}