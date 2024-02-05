package com.example.service

import com.example.model.dto.MessageResponse
import com.example.utils.Connection
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel

interface ChatService {
    suspend fun chat(id: Int, connection: Connection, incoming: ReceiveChannel<Frame>)

    suspend fun list(): Iterable<MessageResponse>
}