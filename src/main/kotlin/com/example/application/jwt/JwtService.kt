package com.example.application.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtConfigService private constructor(secret: String){
    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    fun createAccessToken(id: Int): String = JWT
        .create()
        .withAudience(AUDIENCE)
        .withIssuer(ISSUER)
        .withClaim(CLAIM, id)
        .sign(algorithm)

    companion object {
        private const val ISSUER = "restaurant-app"

        private const val AUDIENCE = "restaurant-app"

        const val CLAIM = "id"

        lateinit var instance: JwtConfigService
            private set

        fun initialize(secret: String) {
            synchronized(this) {
                if(!this::instance.isInitialized) {
                    instance = JwtConfigService(secret)
                }
            }
        }
    }
}