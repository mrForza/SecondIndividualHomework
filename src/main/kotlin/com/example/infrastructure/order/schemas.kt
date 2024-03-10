package com.example.infrastructure.order

import com.example.infrastructure.dish.DishSchema
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object OrderSchema : IntIdTable() {
    val identifier: Column<Int> = integer("identifier")
    val dishes: Column<String> = varchar("dishes", 1024)
    val status: Column<String> = varchar("status", 32)
    val questId: Column<Int> = integer("quest_id")
    val totalCost: Column<Double> = double("total_cost")
}