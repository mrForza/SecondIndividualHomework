package com.example.infrastructure.common

import com.example.domain.common.BaseEntity
import com.example.domain.common.interfaces.BaseRepository

open class ExposedRepository() : BaseRepository() {
    override fun getDetail(identifier: Int): BaseEntity? {
        TODO("Not yet implemented")
    }

    override fun getAll(): MutableList<BaseEntity> {
        TODO("Not yet implemented")
    }

    override fun create(domainEntity: BaseEntity): Int {
        TODO("Not yet implemented")
    }

    override fun update(identifier: Int, updatedValues: Map<String, Any>): Int {
        TODO("Not yet implemented")
    }

    override fun delete(identifier: Int) {
        TODO("Not yet implemented")
    }
}