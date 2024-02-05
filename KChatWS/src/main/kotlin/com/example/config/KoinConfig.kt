package com.example.config

import com.example.controller.handler.ChatHandler
import com.example.controller.handler.UserHandler
import com.example.controller.handler.impl.ChatHandlerImpl
import com.example.controller.handler.impl.UserHandlerImpl
import com.example.service.ChatService
import com.example.service.JwtService
import com.example.service.UserService
import com.example.service.impl.ChatServiceImpl
import com.example.service.impl.JwtServiceImpl
import com.example.service.impl.UserServiceImpl
import org.koin.dsl.module

object KoinConfig {
    val handlers = module {
        single<UserHandler> { UserHandlerImpl() }
        single<ChatHandler> { ChatHandlerImpl() }
    }
    val services = module {
        single<UserService> { UserServiceImpl() }
        single<JwtService> { JwtServiceImpl() }
        single<ChatService> { ChatServiceImpl() }
    }
}