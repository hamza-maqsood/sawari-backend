package config

import com.fdcisl.sawaribackend.controllers.RidesController
import com.fdcisl.sawaribackend.controllers.UsersController
import com.fdcisl.sawaribackend.handlers.RidesHandler
import com.fdcisl.sawaribackend.handlers.UsersHandler
import com.fdcisl.sawaribackend.repo.ridesservice.RidesServiceContractImpl
import com.fdcisl.sawaribackend.repo.usersservice.UsersServiceContractImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object ModulesConfig {

    // MODULES CONSTANTS
    private const val USERS_MODULE = "usersModule"
    private const val USERS_SERVICE = "usersService"
    private const val RIDES_MODULE = "ridesModule"
    private const val RIDES_SERVICE = "ridesService"

    // SERVICES
    private val usersService = Kodein.Module(USERS_SERVICE) {
        bind() from singleton { UsersServiceContractImpl() }
    }

    private val ridesService = Kodein.Module(RIDES_SERVICE) {
        bind() from singleton { RidesServiceContractImpl() }
    }

    // MODULES
    private val usersModule = Kodein.Module(USERS_MODULE) {
        bind() from singleton { UsersController(instance()) }
        bind() from singleton { UsersHandler(instance()) }
        bind() from singleton { instance() }
    }

    private val ridesModule = Kodein.Module(RIDES_MODULE) {
        bind() from singleton { RidesController(instance()) }
        bind() from singleton { RidesHandler(instance()) }
    }


    internal val kodein = Kodein {
        // SERVICES
        import(usersService)
        import(ridesService)

        // MODULES
        import(usersModule)
        import(ridesModule)
    }
}