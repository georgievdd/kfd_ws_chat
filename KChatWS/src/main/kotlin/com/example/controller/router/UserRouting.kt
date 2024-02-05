package com.example.controller.router

import com.example.controller.handler.UserHandler
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRouting() {
    val handler by inject<UserHandler>()
    post("/signup") { handler.signup(call) }
    post("/signin") { handler.signin(call) }
    authenticate("jwt-auth") {
       get("/me") { handler.getSelf(call) }
    }
}