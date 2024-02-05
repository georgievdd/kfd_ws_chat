package com.example.model.exception

import io.ktor.http.*

class UnauthorizedException : AbstractApiException(
    status = HttpStatusCode.Unauthorized,
    message = "Unauthorized"
)