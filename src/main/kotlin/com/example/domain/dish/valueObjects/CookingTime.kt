package com.example.domain.dish.valueObjects

import com.example.domain.common.BaseValueObject
import com.example.domain.dish.INCORRECT_COOKING_TIME_ERROR
import com.example.domain.dish.exceptions.IncorrectCookingTimeException

class CookingTime(value: Double) : BaseValueObject() {
    val cookingTime: Double
    init {
        cookingTime = validateCookingTime(value)
    }

    private fun validateCookingTime(value: Double): Double {
        if (value !in 1.0..150.0) {
            throw IncorrectCookingTimeException(INCORRECT_COOKING_TIME_ERROR)
        }
        return value
    }

    override fun getAtomicValues(): List<Any> {
        return listOf()
    }

    override fun toString(): String {
        return "cooking time: $cookingTime minutes"
    }
}