package com.example.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val email = varchar("email", 255)
    val password = varchar("password", 255)
}