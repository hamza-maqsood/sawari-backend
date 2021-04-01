package config

import org.litote.kmongo.reactivestreams.KMongo

object DBConfig {

    /**
     * using coroutine version of mongo client
     */
    fun setupKMongoInstance() = KMongo.createClient() // TODO add mongo atlas connection uri
}