package com.example.controller.handler

import io.ktor.server.application.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

interface ChatHandler {

    suspend fun chat(session: DefaultWebSocketServerSession)

    suspend fun chatClient(session: DefaultWebSocketServerSession)
    suspend fun list(call: ApplicationCall)

}