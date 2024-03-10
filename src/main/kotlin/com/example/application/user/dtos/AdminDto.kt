package com.example.application.user.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AdminDto (
    val name: String,
    val surname: String,
    val administrationLogin: String
)

@Serializable
data class AdminLoginDto (
    val administrationLogin: String,
    val password: String
)