package com.example.application.user.dtos

import kotlinx.serialization.Serializable


@Serializable
data class QuestDto (
    val name: String,
    val surname: String,
    val email: String,
    val age: Int,
)

@Serializable
data class QuestRegistrationDto (
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val age: Int,
    val balance: Double
)

@Serializable
data class QuestLoginDto (
    val email: String,
    val password: String
)