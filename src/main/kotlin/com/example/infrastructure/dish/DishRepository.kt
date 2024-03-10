package com.example.infrastructure.dish

import com.example.domain.common.BaseEntity
import com.example.domain.dish.entities.DishEntity
import com.example.domain.dish.valueObjects.AvailableCount
import com.example.domain.dish.valueObjects.CookingTime
import com.example.domain.dish.valueObjects.Cost
import com.example.infrastructure.common.ExposedRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class DishRepository() : ExposedRepository() {
    init {
        transaction {
            SchemaUtils.create(DishSchema)
        }
    }

    override fun getDetail(identifier: Int): BaseEntity {
        val domainEntity = transaction {
            DishSchema.select { DishSchema.identifier eq identifier }.map {
                DishEntity(
                    it[DishSchema.identifier],
                    it[DishSchema.name],
                    it[DishSchema.description],
                    CookingTime(it[DishSchema.cookingTime]),
                    AvailableCount(it[DishSchema.availableCount]),
                    Cost(it[DishSchema.cost]),
                ) as DishEntity
            }
        }

        return domainEntity[0]
    }

    override fun getAll(): MutableList<BaseEntity> {
        val domainEntities = transaction {
            DishSchema.selectAll().toMutableList().map {
                DishEntity(
                    it[DishSchema.identifier],
                    it[DishSchema.name],
                    it[DishSchema.description],
                    CookingTime(it[DishSchema.cookingTime]),
                    AvailableCount(it[DishSchema.availableCount]),
                    Cost(it[DishSchema.cost]),
                ) as BaseEntity
            }
        }

        return domainEntities as MutableList<BaseEntity>
    }

    override fun create(domainEntity: BaseEntity): Int {
        if (domainEntity !is DishEntity) {
            return -1
        }

        val identifier = transaction {
            DishSchema.insertAndGetId {
                it[identifier] = domainEntity.identifier;
                it[name] = domainEntity.name;
                it[description] = domainEntity.description;
                it[cookingTime] = domainEntity.getCookingTime()
                it[availableCount] = domainEntity.getAvailableCount()
                it[cost] = domainEntity.getCost()
            }
        }
        return identifier.value
    }

    override fun update(identifier: Int, updatedValues: Map<String, Any>): Int {
        DishSchema.update({DishSchema.identifier eq identifier}) {
            if (updatedValues["name"] != null) {
                it[name] = updatedValues["name"].toString()
            }

            if (updatedValues["description"] != null) {
                it[description] = updatedValues["description"].toString()
            }

            if (updatedValues["cookingTime"] != null) {
                it[cookingTime] = CookingTime(updatedValues["cookingTime"].toString().toDouble()).cookingTime
            }

            if (updatedValues["availableCount"] != null) {
                it[availableCount] = AvailableCount(updatedValues["availableCount"].toString().toInt()).availableCount
            }

            if (updatedValues["cost"] != null) {
                it[cost] = Cost(updatedValues["cost"].toString().toDouble()).cost
            }
        }

        return identifier
    }

    override fun delete(identifier: Int) {
        transaction {
            DishSchema.deleteWhere { DishSchema.identifier eq identifier }
        }
    }
}