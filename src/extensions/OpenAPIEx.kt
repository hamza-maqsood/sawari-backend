package extensions

import com.papsign.ktor.openapigen.route.OpenAPIRoute
import com.papsign.ktor.openapigen.route.throws
import models.ExpectedResponse

fun <T: OpenAPIRoute<T>> T.expectedResponses(responses: List<ExpectedResponse>): T {
    var t: T = throws()
    responses.forEach {
        t = t.throws(it.code, it.desc, Exception::class)
    }
    return t
}