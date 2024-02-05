package com.example

import com.example.config.configureDataBase
import com.example.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureCors()
    configureDataBase()
    configureSocket()
    configureAuth()
    configureRouting()
    configureStatusPages()
}
