package com.fdcisl.sawaribackend.controllers

import com.fdcisl.sawaribackend.handlers.UsersHandler
import com.fdcisl.sawaribackend.models.user.CreateUserRequest
import com.fdcisl.sawaribackend.models.user.UpdateUserRequest
import com.fdcisl.sawaribackend.models.user.User
import extensions.ensureNeitherNullNorBlank
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*

class UsersController(
    private val usersHandler: UsersHandler
) {
    suspend fun addNewUser(call: ApplicationCall) {
        call.receive<CreateUserRequest>().also {
            val addedUser: User? = usersHandler.addNewUser(request = it)
            if (addedUser != null) {
                call.respond(HttpStatusCode.OK, addedUser)
            } else {
                call.respond(HttpStatusCode.NotAcceptable)
            }
        }
    }

    suspend fun getUserById(call: ApplicationCall) {

        val userId = call.parameters["userId"]
        userId.ensureNeitherNullNorBlank()
        // TODO add access authorization
        val user: User? = usersHandler.getUserById(userId = userId!!)
        if (user != null) {
            call.respond(HttpStatusCode.OK, user)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    suspend fun getUsers(call: ApplicationCall) {

        // TODO add access authorization

        val allUsers: List<User> = usersHandler.getAllUsers()
        call.respond(HttpStatusCode.OK, allUsers)
    }

    suspend fun updateUser(call: ApplicationCall) {
        call.receive<UpdateUserRequest>().also {
            // TODO add access authorization
            val updatedUser: User? = usersHandler.updateUser(request = it)
            if (updatedUser != null) {
                call.respond(HttpStatusCode.Accepted, updatedUser)
            } else {
                call.respond(HttpStatusCode.NotAcceptable)
            }
        }
    }

    suspend fun deleteUser(call: ApplicationCall) {
        // TODO add access authorization
        val userId = call.parameters["userId"]
        userId.ensureNeitherNullNorBlank()

        val deleteAck = usersHandler.deleteUserById(userId = userId!!)
        call.respond(if (deleteAck) HttpStatusCode.Accepted else HttpStatusCode.NotAcceptable)
    }
}