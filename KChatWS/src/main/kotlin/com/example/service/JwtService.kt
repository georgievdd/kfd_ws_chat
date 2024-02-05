package com.example.service

import com.auth0.jwt.JWTVerifier
import com.example.database.dao.User
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

interface JwtService {
    fun generate(user: User): String
    fun validate(credential: JWTCredential): JWTPrincipal?
    fun verifier(): JWTVerifier

    fun verifyToken(token: String): JWTPrincipal?
}