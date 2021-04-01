package com.fdcisl.sawaribackend.models.user

data class CreateUserRequest(
    val userDisplayName: String,
    val photoUri: String,
    val nuEmail: String
)