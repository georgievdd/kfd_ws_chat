package com.example.model.exception

import io.ktor.http.*

class InternalServiceException : AbstractApiException(
    status = HttpStatusCode.InternalServerError,
    message = "Server error"
)