package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import java.util.function.Predicate

fun Application.configureCors() {
    install(CORS) {
        anyHost()
        allowHeader("ngrok-skip-browser-warning")
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Connection)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        maxAgeInSeconds = 3600
    }
}