package com.example.domain.order.entities

import com.example.domain.common.BaseEntity
import com.example.domain.dish.entities.DishEntity
import com.example.domain.order.enums.OrderStatus

class OrderUncheckedEntity(
    identifier: Int,
    var dishes: MutableList<String>,
    var status: OrderStatus,
    var questId: Int
) : BaseEntity(identifier) {

}