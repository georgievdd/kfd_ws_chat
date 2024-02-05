package com.example.service.impl

import ch.qos.logback.core.subst.Token
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.database.dao.User
import com.example.service.JwtService
import com.typesafe.config.ConfigFactory
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*

class JwtServiceImpl : JwtService {

    private val config = HoconApplicationConfig(ConfigFactory.load())

    private val issuer = config.property("jwt.issuer").getString()
    private val audience = config.property("jwt.audience").getString()
    private val secret = config.property("jwt.secret").getString()

    private fun algorithm() = Algorithm.HMAC256(secret)

    override fun generate(user: User): String = JWT
        .create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("id", user.id.value)
        .sign(algorithm())

    override fun validate(credential: JWTCredential) =
        if (credential.payload.getClaim("id").asInt() > 0) {
            JWTPrincipal(credential.payload)
        } else {
            null
        }

    override fun verifier(): JWTVerifier = JWT
        .require(algorithm())
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    override fun verifyToken(token: String): JWTPrincipal? {
        val verifier = verifier()
        try {
            val decodedJWT = verifier.verify(token)
            return JWTPrincipal(decodedJWT)
        } catch (e: Exception) {
            return null
        }
    }
}