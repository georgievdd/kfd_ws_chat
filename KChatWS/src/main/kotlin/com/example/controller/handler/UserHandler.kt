package com.example.controller.handler

import io.ktor.server.application.*

interface UserHandler {
    suspend fun signup(call: ApplicationCall)
    suspend fun signin(call: ApplicationCall)
    suspend fun getSelf(call: ApplicationCall)
}