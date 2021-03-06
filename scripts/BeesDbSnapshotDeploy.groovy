includeTargets << grailsScript("Init")
includeTargets << grailsScript("_GrailsBootstrap")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_CheckConfig.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesHelper.groovy")
includeTargets << new File("${cloudBeesPluginDir}/scripts/_BeesCommon.groovy")

USAGE = '''
grails bees-db-snapshot-deploy <snapshotId> [dbId]
    snapshotId  : the snapshot id (retrieved by snapshot list)
	dbId        : the database name (defaults first to environment database name, then application name)
'''

target(beesDbSnapshotDeploy: "Deploy a database snapshot.") {
    depends(checkConfig, prepareClient, loadApp, configureApp)

    String snapshotId = getRequiredArg(0)
    String dbId = buildDbId(1, appCtx)

    def response
    try {
        response = beesClient.databaseSnapshotDeploy(dbId, snapshotId)

    } catch (Exception e) {
        dealWith e
    }

    event "StatusFinal", ["Database snapshot deployed successfully: $response.deployed"]

}

setDefaultTarget(beesDbSnapshotDeploy)
