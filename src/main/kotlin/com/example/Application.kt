package com.example

import com.example.infrastructure.user.AdminRepository
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    try {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/restaurant",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "123"
        )

        embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
    } catch (exception: Exception) {
        println("Во время выполнения программы возникла ошибка! Возможно, вы не подключили Postgres или не установили docker")
    }
}


fun Application.module() {
    configureSecurity()
    configureRouting()
    configureSerialization()
}
