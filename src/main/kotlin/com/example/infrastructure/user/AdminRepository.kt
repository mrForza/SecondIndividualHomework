package com.example.infrastructure.user

import com.example.domain.common.BaseEntity
import com.example.domain.user.entities.AdminEntity
import com.example.domain.user.valueObjects.Balance
import com.example.domain.user.valueObjects.Password
import com.example.infrastructure.common.ExposedRepository
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class AdminRepository() : ExposedRepository() {
    override fun getDetail(identifier: Int): BaseEntity {
        val domainEntity = transaction {
            AdminSchema.select { AdminSchema.identifier eq identifier }.map {
                AdminEntity(
                    it[AdminSchema.identifier],
                    it[AdminSchema.name],
                    it[AdminSchema.surname],
                    it[AdminSchema.administrationLogin],
                    Password(it[AdminSchema.password], false),
                    Balance(it[AdminSchema.balance]),
                ) as BaseEntity
            }
        }

        return domainEntity[0]
    }

    override fun getAll(): MutableList<BaseEntity> {
        val domainEntities = transaction {
            AdminSchema.selectAll().toMutableList().map {
                AdminEntity(
                    it[AdminSchema.identifier],
                    it[AdminSchema.name],
                    it[AdminSchema.surname],
                    it[AdminSchema.administrationLogin],
                    Password(it[AdminSchema.password], false),
                    Balance(it[AdminSchema.balance]),
                ) as BaseEntity
            }
        }

        return domainEntities as MutableList<BaseEntity>
    }

    fun getByAdministrationLogin(login: String): AdminEntity {
        val adminEntity = transaction {
            AdminSchema.select { AdminSchema.administrationLogin eq login }.map {
                AdminEntity(
                    it[AdminSchema.identifier],
                    it[AdminSchema.administrationLogin],
                    it[AdminSchema.name],
                    it[AdminSchema.surname],
                    Password(it[AdminSchema.password], false),
                    Balance(it[AdminSchema.balance]),
                )
            }
        }

        return adminEntity[0]
    }
}