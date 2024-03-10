package com.example.presentation.dish

import com.example.application.dish.DishService
import com.example.application.getIdentifier
import com.example.infrastructure.dish.DishRepository
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.dishRouting() {
    val dishesService = DishService(DishRepository())

    route("/dish") {
        authenticate {
            get("{id}") {
                try {
                    call.respond(
                        dishesService.getDish(
                            getIdentifier(call.parameters["id"]?.toInt())
                        )
                    )
                } catch (exception: Exception) {
                    call.respondText(exception.message.toString(), status = HttpStatusCode.NotFound)
                }
            }
        }

        authenticate {
            get("menu") {
                call.respond(dishesService.getMenu())
            }
        }
    }
}