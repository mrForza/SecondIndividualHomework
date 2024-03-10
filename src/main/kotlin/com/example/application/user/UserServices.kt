@file:Suppress("UNCHECKED_CAST")

package com.example.application.user

import com.example.application.getHashedPassword
import com.example.application.getJwtToken
import com.example.application.jwt.JwtConfigService
import com.example.application.user.dtos.*
import com.example.domain.user.entities.AdminEntity
import com.example.domain.user.entities.QuestEntity
import com.example.domain.user.valueObjects.Balance
import com.example.domain.user.valueObjects.Email
import com.example.domain.user.valueObjects.Password
import com.example.infrastructure.user.AdminRepository
import com.example.infrastructure.user.UserRepository

class UserService(private val userRepository: UserRepository, private val adminRepository: AdminRepository) {
    fun getQuests(): MutableList<QuestDto> {
        val quests = userRepository.getAll() as MutableList<QuestEntity>
        val representation: MutableList<QuestDto> = mutableListOf()
        for (quest in quests) {
            representation.add(
                QuestDto(
                name = quest.name,
                surname = quest.surname,
                email = quest.email.getEmail(),
                age = quest.age
            )
            )
        }
        return representation
    }

    fun getQuest(identifier: Int): QuestDto {
        val questEntity = userRepository.getDetail(identifier) as QuestEntity
        return QuestDto(
            name = questEntity.name,
            surname = questEntity.surname,
            email = questEntity.email.getEmail(),
            age = questEntity.age
        )
    }

    fun getQuestByEmail(email: String): QuestDto {
        val questEntity = userRepository.getByEmail(email) as QuestEntity
        return QuestDto(
            name = questEntity.name,
            surname = questEntity.surname,
            email = questEntity.email.getEmail(),
            age = questEntity.age
        )
    }

    fun getAdmins(): MutableList<AdminDto> {
        val admins = adminRepository.getAll() as MutableList<AdminEntity>
        val representation: MutableList<AdminDto> = mutableListOf()
        for (admin in admins) {
            representation.add(
                AdminDto (
                    name = admin.name,
                    surname = admin.surname,
                    administrationLogin = admin.administrationLogin
                )
            )
        }
        return representation
    }

    fun getAdmin(identifier: Int): AdminDto {
        val adminEntity = adminRepository.getDetail(identifier) as AdminEntity
        return AdminDto (
            name = adminEntity.name,
            surname = adminEntity.surname,
            administrationLogin = adminEntity.administrationLogin
        )
    }
}

class QuestAuthorizationService(private val questRepository: UserRepository) {
    fun register(quest: QuestRegistrationDto): Int {
        return questRepository.create(QuestEntity(
            identifier = (0..Int.MAX_VALUE).random(),
            name = quest.name,
            surname = quest.surname,
            email = Email(quest.email),
            password = Password(quest.password, true),
            age = quest.age,
            balance = Balance(quest.balance)
        ))
    }

    fun login(questCredentials: QuestLoginDto): String {
        val email = questCredentials.email
        val password = questCredentials.password

        val quest = questRepository.getByEmail(email) as QuestEntity
        val hashedPassword = getHashedPassword(password)

        if (quest.password.getPassword() == hashedPassword) {
            return JwtConfigService.instance.createAccessToken(quest.identifier)
        }
        throw Exception("Incorrect administration login or password")
    }

    fun logout(jwtToken: String?): Unit {
        if (jwtToken == null) {
            throw Exception("You haven't be authorized")
        }
        // add jwt to expired tokens
    }
}

class AdminAuthorizationService(private val adminRepository: AdminRepository) {
    fun login(adminCredentials: AdminLoginDto): String {
        val administrationLogin = adminCredentials.administrationLogin
        val password = adminCredentials.password

        val admin = adminRepository.getByAdministrationLogin(administrationLogin)
        val hashedPassword = getHashedPassword(password)

        if (admin.password.getPassword() == hashedPassword) {
            return getJwtToken(admin.name)
        }
        throw Exception("Incorrect administration login or password")
    }

    fun logout(jwtToken: String?): Unit {
        if (jwtToken == null) {
            throw Exception("You haven't be authorized")
        }
        // add jwt to expired tokens
    }
}