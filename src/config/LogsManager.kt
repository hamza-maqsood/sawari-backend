package config

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory

object LogsManager{

    fun disableMongoLogs() {
        val mongoLogger2: Logger = LoggerFactory.getLogger("org.mongodb.driver")  as Logger
        mongoLogger2.level = Level.ERROR
    }
}