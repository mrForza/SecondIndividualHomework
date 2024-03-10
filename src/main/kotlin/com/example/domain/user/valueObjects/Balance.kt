package com.example.domain.user.valueObjects

import com.example.domain.common.BaseValueObject
import com.example.domain.user.INCORRECT_BALANCE_ERROR
import com.example.domain.user.exceptions.IncorrectBalanceException

class Balance(value: Double) : BaseValueObject() {
    private val balance: Double
    init {
        balance = validateBalance(value)
    }

    private fun validateBalance(value: Double) : Double {
        if (value !in 0.0..1_500_000.0) {
            throw IncorrectBalanceException(INCORRECT_BALANCE_ERROR)
        }
        return value
    }
    
    operator fun plus(other: Balance) : Balance {
        return Balance(other.balance + balance)
    }
    
    operator fun minus(other: Balance) : Balance {
        return Balance(balance - other.balance)
    }

    override fun getAtomicValues(): List<Any> {
        return listOf(balance)
    }

    override fun toString(): String {
        return "balance: $balance rub."
    }

    public fun getBalance() : Double {
        return balance
    }
}