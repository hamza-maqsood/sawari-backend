package config

import org.slf4j.LoggerFactory

object AppConfigViewModel {

    fun initApp() {
        val logger = LoggerFactory.getLogger("AppConfig")
        val startTime = System.currentTimeMillis()
        LogsManager.disableMongoLogs()
//        initFirebase()
        val endTime = System.currentTimeMillis()
        logger.debug("Took ${(endTime - startTime)} milliseconds to get the server started!")
    }
}