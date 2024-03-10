package com.example.domain.user.valueObjects

import com.example.domain.common.BaseValueObject
import com.example.domain.user.*
import com.example.domain.user.exceptions.*
import java.math.BigInteger
import java.security.MessageDigest

class Password(value: String, isInitial: Boolean) : BaseValueObject() {
    private var password: String
    init {
        password = if (isInitial) {
            val md = MessageDigest.getInstance("MD5")
            BigInteger(1, md.digest(validatePassword(value)
                .toByteArray())).toString(16)
                .padStart(32, '0')
        } else {
            value
        }
    }

    private fun checkIfSequenceExistsInValue(value: String, sequence: String) : Boolean {
        for (sym in sequence) {
            if (value.contains(sym)) {
                return true
            }
        }
        return false
    }

    private fun validatePassword(value: String) : String {
        if (value.length < 8) {
            throw ShortPasswordLengthException(SHORT_PASSWORD_LENGTH_ERROR)
        }

        if (!checkIfSequenceExistsInValue(value, DIGITS)) {
            throw NoDigitsInPasswordException(NO_DIGITS_IN_PASSWORD_ERROR)
        }

        if (!checkIfSequenceExistsInValue(value, LOWER_LETTERS)) {
            throw NoLowerLettersInPasswordException(NO_LOWER_LETTERS_IN_PASSWORD_ERROR)
        }

        if (!checkIfSequenceExistsInValue(value, UPPER_LETTERS)) {
            throw NoUpperLettersInPasswordException(NO_UPPER_LETTERS_IN_PASSWORD_ERROR)
        }

        if (!checkIfSequenceExistsInValue(value, SPECIAL_SYMBOLS)) {
            throw NoSpecialSymbolsInPasswordException(NO_SPECIAL_SYMBOLS_IN_PASSWORD_ERROR)
        }

        return value
    }

    override fun getAtomicValues(): List<Any> {
        return listOf()
    }

    override fun toString(): String {
        return "password: $password"
    }

    public fun getPassword() : String {
        return password
    }
}
