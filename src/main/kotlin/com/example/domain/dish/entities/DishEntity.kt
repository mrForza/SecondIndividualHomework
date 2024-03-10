package com.example.domain.dish.entities

import com.example.domain.common.BaseEntity
import com.example.domain.dish.valueObjects.AvailableCount
import com.example.domain.dish.valueObjects.CookingTime
import com.example.domain.dish.valueObjects.Cost

class DishEntity(
    identifier: Int,
    var name: String,
    var description: String,
    var cookingTime: CookingTime,
    var availableCount: AvailableCount,
    var cost: Cost
) : BaseEntity(identifier) {
    fun getCost(): Double {
        return cost.cost
    }

    fun getCookingTime(): Double {
        return cookingTime.cookingTime
    }

    fun getAvailableCount(): Int {
        return availableCount.availableCount
    }

    override fun toString(): String {
        return """
            name: $name
            description: $description
            $cookingTime
            $availableCount
            $cost
        """.trimIndent()
    }
}