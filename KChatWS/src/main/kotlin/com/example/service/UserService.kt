package com.example.service

import com.example.model.dto.AuthRequest
import com.example.model.dto.TokenResponse
import com.example.model.dto.UserDto
import io.ktor.server.application.*

interface UserService {
    fun signup(request: AuthRequest): TokenResponse
    fun signin(request: AuthRequest): TokenResponse
    fun findById(id: Int): UserDto
}