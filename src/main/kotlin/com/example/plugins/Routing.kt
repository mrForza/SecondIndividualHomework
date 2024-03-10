package com.example.plugins

import com.example.presentation.dish.dishRouting
import com.example.presentation.order.orderRouting
import com.example.presentation.user.adminRouting
import com.example.presentation.user.userRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    routing {
        userRouting()
        adminRouting()
        dishRouting()
        orderRouting()
    }
}
