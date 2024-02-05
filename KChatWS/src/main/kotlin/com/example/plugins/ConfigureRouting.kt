package com.example.plugins

import com.example.controller.router.chatRouting
import com.example.controller.router.messageRouting
import com.example.controller.router.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() = routing {
    route("/users") { userRouting() }
    route("/chat") { chatRouting() }
    route("/messages") { messageRouting() }
}