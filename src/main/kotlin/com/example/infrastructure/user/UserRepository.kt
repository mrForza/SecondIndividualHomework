package com.example.infrastructure.user

import com.example.domain.common.BaseEntity
import com.example.domain.user.entities.QuestEntity
import com.example.domain.user.valueObjects.Balance
import com.example.domain.user.valueObjects.Email
import com.example.domain.user.valueObjects.Password
import com.example.infrastructure.common.ExposedRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository() : ExposedRepository() {
    init {
        transaction {
            SchemaUtils.create(QuestSchema, AdminSchema)
        }
    }

    override fun getDetail(identifier: Int): BaseEntity {
        val domainEntity = transaction {
            QuestSchema.select { QuestSchema.identifier eq identifier }.map {
                QuestEntity(
                    it[QuestSchema.identifier],
                    it[QuestSchema.name],
                    it[QuestSchema.surname],
                    Email(it[QuestSchema.email]),
                    Password(it[QuestSchema.password], false),
                    it[QuestSchema.age],
                    Balance(it[QuestSchema.balance]),
                ) as BaseEntity
            }
        }

        if (domainEntity.isEmpty()) {
            throw Exception("No user with this id!")
        }

        return domainEntity[0]
    }

    override fun getAll(): MutableList<BaseEntity> {
        val domainEntities = transaction {
            QuestSchema.selectAll().toMutableList().map {
                QuestEntity(
                    it[QuestSchema.identifier],
                    it[QuestSchema.name],
                    it[QuestSchema.surname],
                    Email(it[QuestSchema.email]),
                    Password(it[QuestSchema.password], false),
                    it[QuestSchema.age],
                    Balance(it[QuestSchema.balance]),
                ) as BaseEntity
            }
        }

        return domainEntities as MutableList<BaseEntity>
    }

    fun getByEmail(email: String): BaseEntity {
        val entities = transaction {
            QuestSchema.select { QuestSchema.email eq email }.map {
                QuestEntity(
                    it[QuestSchema.identifier],
                    it[QuestSchema.name],
                    it[QuestSchema.surname],
                    Email(it[QuestSchema.email]),
                    Password(it[QuestSchema.password], false),
                    it[QuestSchema.age],
                    Balance(it[QuestSchema.balance]),
                ) as BaseEntity
            }
        }

        if (entities.isEmpty()) {
            throw Exception("Incorrect credentials!")
        }

        return entities[0] as BaseEntity
    }

    override fun create(domainEntity: BaseEntity): Int {
        if (domainEntity !is QuestEntity) {
            return -1
        }

        val identifier = transaction {
            QuestSchema.insertAndGetId {
                it[identifier] = domainEntity.identifier
                it[name] = domainEntity.name
                it[surname] = domainEntity.surname
                it[email] = domainEntity.email.getEmail()
                it[password] = domainEntity.password.getPassword()
                it[age] = domainEntity.age
                it[balance] = domainEntity.balance.getBalance()
            }
        }
        return identifier.value
    }
}