package com.example.plugins

import com.example.model.exception.AbstractApiException
import com.example.model.exception.InternalServiceException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<AbstractApiException> { call, cause ->
            call.respond(cause.status, cause)
        }

        exception<Throwable> { call, cause ->
            println(cause.stackTrace)
            val exc = InternalServiceException()
            call.respond(exc.status, exc)
        }
    }
}