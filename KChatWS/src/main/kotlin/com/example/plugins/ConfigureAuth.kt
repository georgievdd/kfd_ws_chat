package com.example.plugins

import com.example.service.JwtService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Application.configureAuth() {
    val service by inject<JwtService>()
    authentication {
        jwt("jwt-auth") {
            verifier(service.verifier())
            validate {
                service.validate(it)
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid")
            }
        }
    }
}