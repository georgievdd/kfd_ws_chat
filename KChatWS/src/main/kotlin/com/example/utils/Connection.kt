package com.example.utils

import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class Connection(val session: DefaultWebSocketSession) {
    val id: Int = lastId.getAndIncrement()

    companion object {
        val lastId = AtomicInteger(1)
    }
}