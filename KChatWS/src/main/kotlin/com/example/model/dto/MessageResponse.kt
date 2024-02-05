package com.example.model.dto

import com.example.database.dao.Message

class MessageResponse(
    val id: Int,
    val message: String,
    val author: String,
    val type: String,
)