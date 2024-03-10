package com.example.domain.dish.valueObjects

import com.example.domain.common.BaseValueObject
import com.example.domain.dish.INCORRECT_COST_ERROR
import com.example.domain.dish.exceptions.IncorrectCostException

class Cost(value: Double) : BaseValueObject() {
    val cost: Double
    init {
        cost = validateCost(value)
    }

    private fun validateCost(value: Double): Double {
        if (value !in 150.0..37500.0) {
            throw IncorrectCostException(INCORRECT_COST_ERROR)
        }
        return value
    }

    override fun getAtomicValues(): List<Any> {
        return listOf(cost)
    }

    override fun toString(): String {
        return "cost: $cost rub."
    }
}