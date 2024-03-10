package com.example.infrastructure.dish

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object DishSchema : IntIdTable() {
    val identifier: Column<Int> = integer("identifier")
    val name: Column<String> = varchar("name", 64)
    val description: Column<String> = varchar("description", 4096)
    val cookingTime: Column<Double> = double("cooking_time")
    val availableCount: Column<Int> = integer("available_count")
    val cost: Column<Double> = double("cost")
}