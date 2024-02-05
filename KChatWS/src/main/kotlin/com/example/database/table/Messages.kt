package com.example.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object Messages : IntIdTable() {
    val author = varchar("author", 255)
    val content = varchar("content", 3000)
    val type = varchar("type", 255)
}