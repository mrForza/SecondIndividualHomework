package com.example.infrastructure.user

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object QuestSchema : IntIdTable() {
    val identifier: Column<Int> = integer("identifier")
    val name: Column<String> = varchar("name", 64)
    val surname: Column<String> = varchar("surname", 64)
    val email: Column<String> = varchar("email", 128).uniqueIndex()
    val password: Column<String> = varchar("password", 128)
    val age: Column<Int> = integer("age")
    val balance: Column<Double> = double("balance")
}


object AdminSchema : IntIdTable() {
    val identifier: Column<Int> = integer("identifier")
    val name: Column<String> = varchar("name", 64)
    val surname: Column<String> = varchar("surname", 64)
    val administrationLogin: Column<String> = varchar("login", 64).uniqueIndex()
    val password: Column<String> = varchar("password", 128)
    val balance: Column<Double> = double("balance")
}