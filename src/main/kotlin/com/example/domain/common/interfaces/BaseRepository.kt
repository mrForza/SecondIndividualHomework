package com.example.domain.common.interfaces

import com.example.domain.common.BaseEntity
import java.util.UUID

abstract class BaseRepository() {
    abstract fun getDetail(identifier: Int) : BaseEntity?

    abstract fun getAll() : MutableList<BaseEntity>

    abstract fun create(domainEntity: BaseEntity) : Int

    abstract fun update(identifier: Int, updatedValues: Map<String, Any>): Int

    abstract fun delete(identifier: Int) : Unit
}