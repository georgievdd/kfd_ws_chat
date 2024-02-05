package com.example.controller.router

import com.auth0.jwt.JWT
import com.example.controller.handler.ChatHandler
import com.example.model.exception.UnauthorizedException
import com.example.service.JwtService
import io.ktor.client.engine.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject

fun Route.chatRouting() {
    val handler by inject<ChatHandler>()


    webSocket("/client") {
        handler.chatClient(this)
    }


    authenticate("jwt-auth") {
        webSocket { handler.chat(this) }
    }


    authenticate("jwt-auth") {
        get("/list") { handler.list(call) }
    }

}