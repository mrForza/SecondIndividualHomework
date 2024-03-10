package com.example.domain.common

import org.intellij.lang.annotations.Identifier
import java.util.*

abstract class BaseEntity(val identifier: Int) {

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is BaseEntity) {
            return false
        }

        other as BaseEntity
        return identifier == other.identifier
    }

    override fun hashCode(): Int {
        return identifier.hashCode()
    }

    override fun toString(): String {
        return "Entity №${identifier}"
    }
}