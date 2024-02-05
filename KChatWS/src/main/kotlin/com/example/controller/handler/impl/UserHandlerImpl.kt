package com.example.controller.handler.impl

import com.example.controller.handler.UserHandler
import com.example.model.dto.AuthRequest
import com.example.service.UserService
import com.example.utils.getUserId
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserHandlerImpl : KoinComponent, UserHandler {
    private val service by inject<UserService>()

    override suspend fun signup(call: ApplicationCall) =
        call.respond(service.signup(request = call.receive<AuthRequest>()))

    override suspend fun signin(call: ApplicationCall) =
        call.respond(service.signin(request = call.receive<AuthRequest>()))

    override suspend fun getSelf(call: ApplicationCall) =
        call.respond(service.findById(call.getUserId()))
}