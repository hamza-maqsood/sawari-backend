package com.fdcisl.sawaribackend.routings

import com.fdcisl.sawaribackend.constants.Endpoints
import com.fdcisl.sawaribackend.controllers.UsersController
import com.fdcisl.sawaribackend.models.user.CreateUserRequest
import com.fdcisl.sawaribackend.models.user.UpdateUserRequest
import com.fdcisl.sawaribackend.models.user.User
import com.papsign.ktor.openapigen.annotations.parameters.PathParam
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.*
import com.papsign.ktor.openapigen.route.route
import extensions.expectedResponses
import io.ktor.http.*
import models.ExpectedResponse

fun NormalOpenAPIRoute.usersRoutes(usersController: UsersController) {

    route(Endpoints.USER) {

        expectedResponses(
            listOf(
                ExpectedResponse(
                    code = HttpStatusCode.Created,
                    desc = "When a new user is added successfully"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.NotAcceptable,
                    desc = "When the user is not added due to some error, like a database connection error"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.BadRequest,
                    desc = "This means that the request body is improper."
                )
            )
        ).post<Unit, CreateUserRequest, User>(
            info(
                "route for adding a new user"
            )
        ) { _, _ ->
            usersController.addNewUser(this.pipeline.context)
        }

        data class GetUserParams(
            @PathParam("Id of the user") val userId: String
        )

        expectedResponses(
            listOf(
                ExpectedResponse(
                    code = HttpStatusCode.NotFound,
                    desc = "When the target user is not found"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.Unauthorized,
                    desc = "When the user is trying to access a profile which doesn't belong to them"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.OK,
                    desc = "When the request was successful"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.BadRequest,
                    desc = "When the required params are missing"
                )
            )
        ).get<GetUserParams, User>(
            info(
                "route for getting a user by user id"
            )
        ) {
            usersController.getUserById(this.pipeline.context)
        }

        route("all") {
            expectedResponses(
                listOf(
                    ExpectedResponse(
                        code = HttpStatusCode.Unauthorized,
                        desc = "When the user is not authorized to access all users"
                    ),
                    ExpectedResponse(
                        code = HttpStatusCode.OK,
                        desc = "When the request was successful"
                    )
                )
            ).get<Unit, List<User>>(
                info(
                    "Route for getting all users"
                )
            ) {
                usersController.getUsers(this.pipeline.context)
            }
        }

        expectedResponses(
            listOf(
                ExpectedResponse(
                    code = HttpStatusCode.BadRequest,
                    desc = "This means that the required body is improper."
                ),
                ExpectedResponse(
                    code = HttpStatusCode.NotAcceptable,
                    desc = "When the target user is not found or some other error occured"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.Unauthorized,
                    desc = "When the user is trying to update to a user which doesn't belongs to them"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.Accepted,
                    desc = "When the request was successful and the user was updated."
                )
            )
        ).put<Unit, User, UpdateUserRequest>(
            info(
                "Route for updating a user"
            )
        ) { _, _ ->
            usersController.updateUser(this.pipeline.context)
        }

        data class DeleteUserParams(
            @PathParam("Id of the user to delete") val userId: String
        )

        expectedResponses(
            listOf(
                ExpectedResponse(
                    code = HttpStatusCode.BadRequest,
                    desc = "This means that the required params are missing"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.NotAcceptable,
                    desc = "When the target user is not found or some other error happened"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.Unauthorized,
                    desc = "When the user is trying to delete a user which is not in their access"
                ),
                ExpectedResponse(
                    code = HttpStatusCode.OK,
                    desc = "When the request was successful and the user was deleted."
                )
            )
        ).delete<DeleteUserParams, Unit>(
            info(
                "route for deleting a user"
            )
        ) {
            usersController.deleteUser(this.pipeline.context)
        }
    }
}