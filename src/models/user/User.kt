package com.fdcisl.sawaribackend.models.user

import org.bson.codecs.pojo.annotations.BsonId

data class User(
    @BsonId
    val userId: String,
    val displayName: String,
    val photoUri: String,
    val nuEmail: String
)