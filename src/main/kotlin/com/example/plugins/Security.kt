package com.example.plugins

import com.example.application.jwt.JwtConfigService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

data class QuestIdPrincipalForQuest(val id: Int): Principal

fun Application.configureSecurity() {
    JwtConfigService.initialize("secret_key")

    authentication {
        jwt {
            verifier(JwtConfigService.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfigService.CLAIM).asInt()
                if(claim != null) {
                    QuestIdPrincipalForQuest(claim)
                } else {
                    null
                }
            }
        }
    }
}
