package com.example.presentation.user

import com.example.application.user.QuestAuthorizationService
import com.example.application.user.UserService
import com.example.application.user.dtos.QuestRegistrationDto
import com.example.application.getIdentifier
import com.example.application.user.AdminAuthorizationService
import com.example.application.user.dtos.AdminLoginDto
import com.example.application.user.dtos.QuestLoginDto
import com.example.domain.common.BaseException
import com.example.infrastructure.user.AdminRepository
import com.example.infrastructure.user.UserRepository
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRouting() {
    val userService = UserService(UserRepository(), AdminRepository())
    val authorizationService = QuestAuthorizationService(UserRepository())

    route ("/quest") {
        get {
            call.respond(userService.getQuests())
        }
        get ("{id}") {
            try {
                call.respond(
                    userService.getQuest(
                        getIdentifier(call.parameters["id"]?.toInt())
                    )
                )
            } catch (exception: Exception) {
                call.respondText(exception.message.toString(), status = HttpStatusCode.NotFound)
            }
        }
        post ("register") {
            try {
                val quest = call.receive<QuestRegistrationDto>()
                val identifier = authorizationService.register(quest)
                call.respondText("The quest with id: $identifier was successfully created!")
            } catch (exception: BaseException) {
                call.respondText(exception.message.toString(), status = HttpStatusCode.BadRequest)
            }
        }
        post ("login") {
            try {
                val questCredentials = call.receive<QuestLoginDto>()
                val token = authorizationService.login(questCredentials)
                call.respondText(("Your jwt token:$token\n\nYou should past it in Headers"))
            } catch (exception: Exception) {
                call.respondText(exception.message.toString(), status = HttpStatusCode.BadRequest)
            }
        }
        get ("logout") {
            try {
                val jwtToken = call.request.headers["Authorization"]
                authorizationService.logout(jwtToken)
                call.respondText("You have been successfully logout")
            } catch (exception: Exception) {
                call.respondText(exception.message.toString(), status = HttpStatusCode.BadRequest)
            }
        }
    }
}


fun Route.adminRouting() {
    val userService = UserService(UserRepository(), AdminRepository())
    val authorizationService = AdminAuthorizationService(AdminRepository())

    route ("/administration") {
        get {
            call.respond(userService.getAdmins())
        }
        get ("{id}") {
            try {
                call.respond(
                    userService.getAdmin(
                        getIdentifier(call.parameters["id"]?.toInt())
                    )
                )
            } catch (exception: Exception) {
                call.respondText(exception.message.toString())
            }
        }
        post ("login") {
            try {
                val adminCredentials = call.receive<AdminLoginDto>()
                val token = authorizationService.login(adminCredentials)
                call.respondText(("Your jwt token:$token\n\nYou should past it in Headers"))
            } catch (exception: Exception) {
                call.respondText(exception.message.toString(), status = HttpStatusCode.BadRequest)
            }
        }
        get ("logout") {
            try {
                val jwtToken = call.request.headers["Authorization"]
                authorizationService.logout(jwtToken)
                call.respondText("You have been successfully logout")
            } catch (exception: Exception) {
                call.respondText(exception.message.toString(), status = HttpStatusCode.BadRequest)
            }
        }
    }
}
