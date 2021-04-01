package config

import com.fdcisl.sawaribackend.controllers.RidesController
import com.fdcisl.sawaribackend.controllers.UsersController
import com.fdcisl.sawaribackend.exceptions.AuthenticationException
import com.fdcisl.sawaribackend.routings.ridesRoutes
import com.fdcisl.sawaribackend.routings.usersRoutes
import com.papsign.ktor.openapigen.OpenAPIGen
import com.papsign.ktor.openapigen.openAPIGen
import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.tag
import extensions.respondError
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import models.Tags
import org.kodein.di.generic.instance
import org.slf4j.event.Level

@EngineAPI
fun server(
    engine: ApplicationEngineFactory<BaseApplicationEngine,
            out ApplicationEngine.Configuration>
): BaseApplicationEngine {
    initializeApp()
    return embeddedServer(
        engine,
        watchPaths = listOf("config.mainModule"),
        module = Application::mainModule
    )
}

fun initializeApp() {
    AppConfigViewModel.initApp()
}

@EngineAPI
fun setup() = server(Netty)

@EngineAPI
fun Application.mainModule() {

    val usersController: UsersController by ModulesConfig.kodein.instance()
    val ridesController: RidesController by ModulesConfig.kodein.instance()

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(ContentNegotiation) {
        gson()
    }

    install(StatusPages) {
        exception<AuthenticationException> {
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<Throwable> { cause ->
            cause.printStackTrace()
            call.respondError(
                error = cause.message
            )
        }
    }

//    authentication { // TODO this feature will be used later when adding auth layer
//        firebase("firebase", FirebaseApp.getInstance()) {
//            validate { credential ->
//                FirebasePrincipal(
//                    userId = credential.token.uid,
//                    phone = credential.token.claims["phone_number"].toString()
//                )
//            }
//        }
//    }

    install(OpenAPIGen) {
        // basic info
        info {
            version = "0.0.1"
            title = "FDC Carpooling"
            description = "The FDC Carpooling App Backend API"
            contact {
                name = "Fast Developers Club"
                email = "fdc@nu.edu.pk"
            }
        }
        // describe the server, add as many as you want
        server("http://localhost:8080/") {
            description = "Sawari Backend Server"
        }
    }

    // this allows the request body to be consumed twice
    install(DoubleReceive)

    apiRouting {

        this.ktorRoute.get("/openapi.json") {
            call.respond(application.openAPIGen.api.serialize())
        }
        this.ktorRoute.get("/") {
            call.respondRedirect("/swagger-ui/index.html?url=/openapi.json", true)
        }
        this.ktorRoute.get("/health") {
            call.respondText("")
            val runtime = Runtime.getRuntime()
            StringBuilder().apply {
                append("Sawari server is up and running :)")
                append("Runtime.getRuntime().availableProcessors(): ${runtime.availableProcessors()}")
                append("Runtime.getRuntime().freeMemory(): ${runtime.freeMemory()}")
                append("Runtime.getRuntime().totalMemory(): ${runtime.totalMemory()}")
                append("Runtime.getRuntime().maxMemory(): ${runtime.maxMemory()}")
            }
        }

        tag(Tags.USERS) {
            usersRoutes(usersController = usersController)
        }
        tag(Tags.RIDES) {
            ridesRoutes(ridesController = ridesController)
        }
    }
}