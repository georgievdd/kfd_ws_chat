package com.example.service.impl

import com.example.database.dao.User
import com.example.database.table.Users
import com.example.model.dto.AuthRequest
import com.example.model.dto.TokenResponse
import com.example.model.dto.UserDto
import com.example.model.exception.AlreadyExistsException
import com.example.model.exception.NotFoundException
import com.example.model.exception.UnauthorizedException
import com.example.service.JwtService
import com.example.service.UserService
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserServiceImpl() : KoinComponent, UserService {

    private val jwtService by inject<JwtService>()

    private fun hash(message: String) =
        message.hashCode().toString()

    override fun signup(request: AuthRequest): TokenResponse {
        if (
            transaction {
                User.find(Users.email eq request.email).singleOrNull()
            } != null
            ) throw AlreadyExistsException()
        val user = transaction {
            User.new {
                email = request.email
                password = hash(request.password)
            }
        }
        val token = jwtService.generate(user)
        return token.mapToTokenResponse()
    }

    override fun signin(request: AuthRequest): TokenResponse {
        val user = transaction {
            val user = User.find { Users.email eq request.email }.singleOrNull() ?: throw NotFoundException()
            if (user.password != hash(request.password)) {
                throw UnauthorizedException()
            }
            return@transaction user
        }
        val token = jwtService.generate(user)
        return token.mapToTokenResponse()
    }

    override fun findById(id: Int): UserDto {
        val user = transaction { User.findById(id) ?: throw NotFoundException() }
        return user.mapToUserDto()
    }


    private fun String.mapToTokenResponse() = TokenResponse(this)
    private fun User.mapToUserDto() = UserDto(email = email, id = id.value)
}