package com.example.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

fun getIdentifier(nullId: Int?): Int {
    return nullId?.toInt() ?: 0
}

fun getJwtToken(name: String): String {
    return JWT.create()
        .withAudience("http://127.0.0.1:8080/quest/login")
        .withIssuer("http://127.0.0.1:8080/")
        .withClaim("name", name)
        .withExpiresAt(Date(System.currentTimeMillis() + 60000))
        .sign(Algorithm.HMAC256("secret"))
}

fun getHashedPassword(rawPassword: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(rawPassword
        .toByteArray())).toString(16)
        .padStart(32, '0')
}