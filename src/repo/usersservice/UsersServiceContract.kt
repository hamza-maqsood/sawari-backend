package com.fdcisl.sawaribackend.repo.usersservice

import com.fdcisl.sawaribackend.models.user.UpdateUserRequest
import com.fdcisl.sawaribackend.models.user.User

interface UsersServiceContract {
    suspend fun addNewUser(user: User): Boolean
    suspend fun deleteUserById(userId: String): Boolean
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(userId: String): User?
    suspend fun updateUser(request: UpdateUserRequest): Boolean
}