package com.fdcisl.sawaribackend.application

import config.setup
import io.ktor.server.engine.*

@EngineAPI
fun main() {
    setup().start(wait = true)
}