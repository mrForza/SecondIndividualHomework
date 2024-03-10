package com.example.application.order.dtos

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto (
    val dishes: MutableList<String>,
    val questId: Int
)

@Serializable
data class OrderReceiveDto (
    val dishes: MutableList<String>,
    val status: String,
    val totalCost: Double
)

@Serializable
data class QuestInfoDto (
    val questId: Int
)