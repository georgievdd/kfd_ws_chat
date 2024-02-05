package com.example.service.impl

import com.example.database.dao.Message
import com.example.database.dao.User
import com.example.database.table.Messages
import com.example.model.dto.MessageRequest
import com.example.model.dto.MessageResponse
import com.example.model.exception.NotFoundException
import com.example.service.ChatService
import com.example.utils.Connection
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel
import org.jetbrains.exposed.sql.transactions.transaction

class ChatServiceImpl : ChatService {
    private val connections = mutableMapOf<Int, MutableSet<Connection>>()
    private val mapper = jacksonObjectMapper()

    private suspend fun connect(id: Int, connection: Connection) {
        val userConnections = connections.getOrDefault(id, mutableSetOf()).also { it += connection }
        connections[id] = userConnections
    }

    private suspend fun processFrame(id: Int, frame: Frame.Text) {
        val request = frame.mapToMessageRequest()
        var email: String = ""
        val message = transaction {
            val user = User.findById(id) ?: throw NotFoundException()
            email = user.email
            Message.new {
                author = email
                content = request.message
                type = request.type
            }
        }
        val response = message.mapToMessageResponse()
        val responseFrame = mapper.writeValueAsString(response).apply {
            Frame.Text(this)
        }
        connections.values.flatten().forEach { it.session.send(responseFrame) }
    }

    private suspend fun processFrames(id: Int, incoming: ReceiveChannel<Frame>) {
        for (frame in incoming) {
            if (frame is Frame.Text) {
                processFrame(id, frame)
            }
        }
    }

    private suspend fun disconnect(id: Int, connection: Connection) {
        connections.getOrDefault(id, null)?.also { it -= connection }
    }

    override suspend fun chat(id: Int, connection: Connection, incoming: ReceiveChannel<Frame>) {
        connect(id, connection)
        try {
            processFrames(id, incoming)
        } finally {
            disconnect(id, connection)
        }
    }

    override suspend fun list(): Iterable<MessageResponse> =
        transaction {
            Message.all().map { it.mapToMessageResponse() }
        }


    private fun Frame.Text.mapToMessageRequest() =
        mapper.readValue<MessageRequest>(readText())

    private fun Message.mapToMessageResponse() =
        MessageResponse(
            id = id.value,
            message = content,
            author = author,
            type = type,
        )
}