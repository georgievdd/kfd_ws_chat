package com.example.plugins

import com.example.config.KoinConfig
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(KoinConfig.handlers, KoinConfig.services)
    }
    startKoin {
        modules(KoinConfig.handlers, KoinConfig.services)
    }
}