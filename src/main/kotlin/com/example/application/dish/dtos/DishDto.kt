package com.example.application.dish.dtos

import kotlinx.serialization.Serializable

@Serializable
data class DishDto (
    val name: String,
    val description: String,
    val cookingTome: Double,
    val availableCount: Int,
    val cost: Double
)