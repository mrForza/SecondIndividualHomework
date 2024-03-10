package com.example.domain.dish.valueObjects

import com.example.domain.common.BaseValueObject
import com.example.domain.dish.INCORRECT_AVAILABLE_COUNT_ERROR
import com.example.domain.dish.exceptions.IncorrectAvailableCountException

class AvailableCount(value: Int) : BaseValueObject() {
    var availableCount: Int
    init {
        availableCount = validateAvailableCount(value)
    }

    private fun validateAvailableCount(value: Int): Int {
        if (value !in 1..1024) {
            throw IncorrectAvailableCountException(INCORRECT_AVAILABLE_COUNT_ERROR)
        }
        return value
    }

    override fun getAtomicValues(): List<Any> {
        return listOf(availableCount)
    }
}