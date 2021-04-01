package com.fdcisl.sawaribackend.repo.usersservice

import com.fdcisl.sawaribackend.constants.DbCollections
import com.fdcisl.sawaribackend.models.user.UpdateUserRequest
import com.fdcisl.sawaribackend.models.user.User
import com.fdcisl.sawaribackend.repo.BaseService
import com.mongodb.reactivestreams.client.MongoCollection
import extensions.awaitDeleteAcknowledgement
import extensions.awaitInsertAcknowledgement
import extensions.awaitUpdateAcknowledgement
import extensions.dbQuery
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo

class UsersServiceContractImpl: BaseService(), UsersServiceContract {

    private val usersCollection: MongoCollection<User> =
        mongoDBInstance.getCollection(DbCollections.USERS, User::class.java)

    override suspend fun addNewUser(user: User): Boolean = dbQuery {
        return@dbQuery usersCollection.insertOne(
            user
        ).awaitInsertAcknowledgement()
    }

    override suspend fun deleteUserById(userId: String): Boolean = dbQuery {
        return@dbQuery usersCollection.deleteOne(
            User::userId eq userId
        ).awaitDeleteAcknowledgement()
    }

    override suspend fun getAllUsers(): List<User> = dbQuery {
        return@dbQuery usersCollection.find()
            .toList()
    }

    override suspend fun getUserById(userId: String): User? = dbQuery {
        return@dbQuery usersCollection.find(
            User::userId eq userId
        ).awaitFirstOrNull()
    }

    override suspend fun updateUser(request: UpdateUserRequest): Boolean = dbQuery {
        request.newUserDisplayNameValue?.let {
            val ack = usersCollection.updateOne(
                User::userId eq request.userId, set(
                    User::displayName setTo it
                )
            ).awaitUpdateAcknowledgement()
            if (!ack) return@dbQuery false
        }
        request.newPhotoUriValue?.let {
            val ack = usersCollection.updateOne(
                User::userId eq request.userId, set(
                    User::photoUri setTo it
                )
            ).awaitUpdateAcknowledgement()
            if (!ack) return@dbQuery false
        }
        return@dbQuery true
    }
}