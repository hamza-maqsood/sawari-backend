package com.fdcisl.sawaribackend.models.user

data class UpdateUserRequest(
    val userId: String,
    val newUserDisplayNameValue: String?,
    val newPhotoUriValue: String?
)