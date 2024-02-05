package com.example.model.exception

import io.ktor.http.*

class AlreadyExistsException : AbstractApiException(
    status = HttpStatusCode.BadRequest,
    message = "Resource already exists"
)