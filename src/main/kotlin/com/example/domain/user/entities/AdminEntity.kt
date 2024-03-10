package com.example.domain.user.entities

import com.example.domain.common.BaseEntity
import com.example.domain.user.valueObjects.Balance
import com.example.domain.user.valueObjects.Password
import java.util.UUID

class AdminEntity(
    identifier: Int,
    val name: String,
    var surname: String,
    val administrationLogin: String,
    var password: Password,
    private var balance: Balance
) : BaseEntity(identifier) {
    override fun toString(): String {
        return """
            name: $name
            surname: $surname
            admin login: $administrationLogin
            $password
            $balance
        """.trimIndent()
    }
}