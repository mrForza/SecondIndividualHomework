package com.example.application.order

import com.example.application.order.dtos.OrderDto
import com.example.application.order.dtos.OrderReceiveDto
import com.example.domain.order.entities.OrderUncheckedEntity
import com.example.domain.order.enums.OrderStatus
import com.example.infrastructure.order.OrderRepository

class OrdersService(private val orderRepository: OrderRepository) {
    fun getOrderByQuestId(identifier: Int): OrderReceiveDto {
        val order = orderRepository.getOrderByQuestIdentifier(identifier)
        val dishesNames = mutableListOf<String>()

        for (dishEntity in order.dishes) {
            dishesNames.add(dishEntity.name)
        }

        return OrderReceiveDto(
            dishes = dishesNames,
            status = order.status.toString(),
            totalCost = order.totalCost
        )
    }

    fun makeOrder(order: OrderDto): Int {
        val uncheckedOrderEntity = OrderUncheckedEntity(
            identifier = (0..Int.MAX_VALUE).random(),
            dishes = order.dishes,
            status = OrderStatus.PREPARED,
            questId = order.questId
        )

        return orderRepository.create(uncheckedOrderEntity)
    }
}