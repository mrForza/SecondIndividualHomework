package com.example.domain.user.valueObjects

import com.example.domain.common.BaseValueObject
import com.example.domain.user.BAD_EMAIL_REGEX_ERROR
import com.example.domain.user.exceptions.BadEmailRegexException

class Email(value: String) : BaseValueObject() {
    private val username: String
    init {
        username = validateEmail(value).split("@")[0]
    }

    private val servername: String
    init {
        servername = validateEmail(value).split("@")[1]
    }

    override fun getAtomicValues(): List<Any> {
        return listOf(username, servername)
    }

    private fun validateEmail(value: String) : String {
        val regex = Regex("[a-zA-Z0-9_]+@[a-zA-Z]+.[a-zA-Z]{2,6}")
        if (!regex.matches(value)) {
            throw BadEmailRegexException(BAD_EMAIL_REGEX_ERROR)
        }
        return value
    }

    override fun toString(): String {
        return "email: $username@$servername"
    }

    public fun getEmail() : String {
        return "$username@$servername"
    }
}