package models

import io.ktor.http.*

data class ExpectedResponse(
    val code: HttpStatusCode,
    val desc: String? = null
)