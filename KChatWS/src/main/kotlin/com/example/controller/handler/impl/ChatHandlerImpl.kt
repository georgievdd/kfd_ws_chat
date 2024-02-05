package com.example.controller.handler.impl

import com.example.controller.handler.ChatHandler
import com.example.model.exception.UnauthorizedException
import com.example.service.ChatService
import com.example.service.JwtService
import com.example.utils.Connection
import com.example.utils.getUserId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.ktor.ext.inject

class ChatHandlerImpl : KoinComponent, ChatHandler {
    private val chatService by inject<ChatService>()
    val jwtService by inject<JwtService>()

    override suspend fun chat(session: DefaultWebSocketServerSession) {
        val userId = session.call.getUserId()
        val connection = Connection(session)
        chatService.chat(userId, connection, session.incoming)
    }

    override suspend fun chatClient(session: DefaultWebSocketServerSession) {
        val token = session.call.request.queryParameters["token"] ?: throw UnauthorizedException()
        session.call.authentication.principal = jwtService.verifyToken(token) ?: throw UnauthorizedException()
        chat(session)
    }

    override suspend fun list(call: ApplicationCall) =
        call.respond(chatService.list())
}