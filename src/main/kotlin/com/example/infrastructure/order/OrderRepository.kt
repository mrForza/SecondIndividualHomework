package com.example.infrastructure.order

import com.example.domain.common.BaseEntity
import com.example.domain.dish.entities.DishEntity
import com.example.domain.dish.valueObjects.AvailableCount
import com.example.domain.dish.valueObjects.CookingTime
import com.example.domain.dish.valueObjects.Cost
import com.example.domain.order.entities.OrderEntity
import com.example.domain.order.entities.OrderUncheckedEntity
import com.example.domain.order.enums.OrderStatus
import com.example.infrastructure.common.ExposedRepository
import com.example.infrastructure.dish.DishRepository
import com.example.infrastructure.dish.DishSchema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class OrderRepository() : ExposedRepository() {
    init {
        transaction {
            SchemaUtils.create(OrderSchema)
        }
    }

    fun getOrderByQuestIdentifier(questIdentifier: Int): OrderEntity {
        val orderEntity = transaction {
            OrderSchema.select { OrderSchema.questId eq questIdentifier }.map {
                OrderEntity(
                    identifier = it[OrderSchema.identifier],
                    questId = it[OrderSchema.questId],
                    status = OrderStatus.valueOf(it[OrderSchema.status]),
                    dishes = mutableListOf()
                )
            }
        }
        val query = transaction {
            OrderSchema.select { OrderSchema.questId eq questIdentifier }.map {
                DishComplexEntity(
                    names = it[OrderSchema.dishes]
                )
            }
        }

        val dishNames = query[0].names.split(" ")

        if (orderEntity.isEmpty()) {
            throw Exception("You haven't made order")
        }

        for (dishName in dishNames) {
            val dishEntity = transaction {
                DishSchema.select { DishSchema.name eq dishName }.map {
                    DishEntity(
                        it[DishSchema.identifier],
                        it[DishSchema.name],
                        it[DishSchema.description],
                        CookingTime(it[DishSchema.cookingTime]),
                        AvailableCount(it[DishSchema.availableCount]),
                        Cost(it[DishSchema.cost]),
                    )
                }
            }
            if (dishEntity.isEmpty()) {
                throw Exception("Your order is empty!")
            }
            orderEntity[0].dishes.add(dishEntity[0])
        }

        return orderEntity[0]
    }

    override fun create(domainEntity: BaseEntity): Int {
        domainEntity as OrderUncheckedEntity
        var dishesString = ""
        for (dish in domainEntity.dishes) {
            dishesString += dish
        }

        println("!!!!!!!!!!!!!!!!!!!!!!!!")
        val identifier = transaction {
            OrderSchema.insertAndGetId {
                it[identifier] = (0..Int.MAX_VALUE).random()
                it[status] = domainEntity.status.toString()
                it[questId] = domainEntity.questId
                it[dishes] = dishesString
                it[totalCost] = 0.0
            }
        }

        return identifier.value
    }

    data class DishComplexEntity(val names: String)
}