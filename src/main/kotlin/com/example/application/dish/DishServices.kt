package com.example.application.dish

import com.example.application.dish.dtos.DishDto
import com.example.domain.dish.entities.DishEntity
import com.example.infrastructure.dish.DishRepository

class DishService(private val dishRepository: DishRepository) {
    fun getMenu(): MutableList<DishDto> {
        val dishes = dishRepository.getAll()
        val representation: MutableList<DishDto> = mutableListOf()
        for (dish in dishes) {
            dish as DishEntity
            representation.add(DishDto(
                name = dish.name,
                description = dish.description,
                cookingTome = dish.cookingTime.cookingTime,
                availableCount = dish.availableCount.availableCount,
                cost = dish.cost.cost
            ))
        }

        return representation
    }

    fun getDish(identifier: Int): DishDto {
        val dishEntity = dishRepository.getDetail(identifier) as DishEntity
        return DishDto(
            name = dishEntity.name,
            description = dishEntity.description,
            cookingTome = dishEntity.cookingTime.cookingTime,
            availableCount = dishEntity.availableCount.availableCount,
            cost = dishEntity.cost.cost
        )
    }
}