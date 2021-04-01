package com.fdcisl.sawaribackend.repo

import com.fdcisl.sawaribackend.constants.AppConstants
import com.mongodb.reactivestreams.client.MongoDatabase
import config.DBConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseService: CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    protected val mongoDBInstance: MongoDatabase by lazy {
        DBConfig.setupKMongoInstance().getDatabase(AppConstants.PRODUCTION_DATABASE)
    }
}