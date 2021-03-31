package com.fdcisl.sawaribackend.utils

import org.bson.types.ObjectId

fun generateUniqueId(): String = ObjectId.get().toHexString()