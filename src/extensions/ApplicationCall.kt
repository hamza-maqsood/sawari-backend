package extensions

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*

suspend fun ApplicationCall.respondError(
    error: String?,
    httpStatusCode: HttpStatusCode = HttpStatusCode.BadRequest
) = respond(httpStatusCode, mapOf("error" to error))