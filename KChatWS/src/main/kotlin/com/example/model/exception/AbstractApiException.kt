package com.example.model.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.ktor.http.*

@JsonIgnoreProperties("cause", "stackTrace", "suppresed", "localizedMessage")
abstract class AbstractApiException(
    val status: HttpStatusCode,
    override val message: String,
) : Exception()