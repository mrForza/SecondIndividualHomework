package com.example.presentation.order

import com.example.application.jwt.JwtConfigService
import com.example.application.order.OrdersService
import com.example.application.order.dtos.OrderDto
import com.example.application.order.dtos.QuestInfoDto
import com.example.infrastructure.order.OrderRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orderRouting() {
    val ordersService = OrdersService(OrderRepository())

    route("/order") {
        authenticate {
            get ("my") {
                try {
                    val questDto = call.receive<QuestInfoDto>()
                    val order = ordersService.getOrderByQuestId(questDto.questId)
                    call.respond(order)
                } catch (exception: Exception) {
                    call.respondText(exception.message.toString(), status = HttpStatusCode.BadRequest)
                }
            }
        }

        authenticate {
            get ("pay") {

            }
        }


        authenticate {
            get ("status") {
                try {
                    val order = ordersService.getOrderByQuestId(1)
                    call.respondText(
                        "Your order's status: ${order.status}",
                        status = HttpStatusCode.OK
                    )
                } catch (exception: Exception) {
                    call.respondText(exception.message.toString(), status = HttpStatusCode.BadRequest)
                }
            }
        }

        authenticate {
            post ("make") {
                try {
                    val orderDto = call.receive<OrderDto>()
                    ordersService.makeOrder(orderDto)
                    call.respondText("Your order have been successfully created! status = PREPARED")
                } catch (exception: Exception) {
                    call.respondText(exception.message.toString(), status = HttpStatusCode.BadRequest)
                }
            }
        }
    }
}