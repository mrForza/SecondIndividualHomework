package com.example.domain.user.entities

import com.example.domain.common.BaseEntity
import com.example.domain.user.valueObjects.Balance
import com.example.domain.user.valueObjects.Email
import com.example.domain.user.valueObjects.Password

class QuestEntity(
    identifier: Int,
    var name: String,
    var surname: String,
    var email: Email,
    var password: Password,
    var age: Int,
    var balance: Balance) : BaseEntity(identifier) {
    override fun toString(): String {
        return """
            name: $name
            surname: $surname
            $email
            $password
            age: $age
            $balance
        """.trimIndent()
    }
}