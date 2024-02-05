package com.example.controller.router

import com.example.controller.handler.ChatHandler
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject

fun Route.messageRouting() {
    val handler by inject<ChatHandler>()

    authenticate("jwt-auth") {
        get("/list") { handler.list(call) }
    }
}