package com.example.config

import com.example.database.table.Messages
import com.example.database.table.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

val config = HikariConfig().apply {
    jdbcUrl = "jdbc:mysql://db:3306/kbackend?createDatabaseIfNotExist=true"
    driverClassName = "com.mysql.cj.jdbc.Driver"
    username = "root"
    password = "password"
    maximumPoolSize = 10
}

fun configureDataBase() {
    val source = HikariDataSource(config)
    Database.connect(source)
    transaction {
        SchemaUtils.create(
            Users,
            Messages,
        )
    }
}