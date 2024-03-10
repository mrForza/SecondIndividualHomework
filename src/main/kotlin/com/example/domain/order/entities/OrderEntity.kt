package com.example.domain.order.entities

import com.example.domain.common.BaseEntity
import com.example.domain.dish.entities.DishEntity
import com.example.domain.order.enums.OrderStatus

class OrderEntity(
    identifier: Int,
    var dishes: MutableList<DishEntity>,
    var status: OrderStatus,
    var questId: Int
) : BaseEntity(identifier) {
    var totalCost: Double
    init {
        totalCost = calculateTotalCost()
    }

    private fun calculateTotalCost(): Double {
        var totalCost = 0.0
        for (dish in dishes) {
            totalCost += dish.getCost()
        }
        return totalCost
    }

    fun getStatus(): String {
        return when (status) {
            OrderStatus.ACCEPTED -> {
                "The order is accepted"
            }
            OrderStatus.PREPARED -> {
                "The order is being prepared"
            }
            OrderStatus.READY -> {
                "The order is ready"
            }
        }
    }
}