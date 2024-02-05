package com.example.database.dao

import com.example.database.table.Messages
import com.example.database.table.Users
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Message(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Message>(Messages)
    var content by Messages.content
    var author by Messages.author
    var type by Messages.type
}