package com.fdcisl.sawaribackend.handlers

import com.fdcisl.sawaribackend.models.user.CreateUserRequest
import com.fdcisl.sawaribackend.models.user.UpdateUserRequest
import com.fdcisl.sawaribackend.models.user.User
import com.fdcisl.sawaribackend.repo.usersservice.UsersServiceContract
import com.fdcisl.sawaribackend.utils.generateUniqueId

class UsersHandler(
    private val usersService: UsersServiceContract
) {

    suspend fun addNewUser(request: CreateUserRequest): User? {
        val userId = generateUniqueId()
        val user = User(
            userId = userId,
            displayName = request.userDisplayName,
            photoUri = request.photoUri,
            nuEmail = request.nuEmail
        )
        val addAck = usersService.addNewUser(user = user)
        return if (addAck) {
            user
        } else {
            null
        }
    }

    suspend fun deleteUserById(userId: String): Boolean {

        return usersService.deleteUserById(userId = userId)
    }

    suspend fun updateUser(request: UpdateUserRequest): User? {
        val updateAck = usersService.updateUser(request = request)
        return if (updateAck) {
            usersService.getUserById(userId = request.userId)
        } else {
            null
        }
    }

    suspend fun getAllUsers(): List<User> {
        return usersService.getAllUsers()
    }

    suspend fun getUserById(userId: String): User? {
        return usersService.getUserById(userId = userId)
    }
}