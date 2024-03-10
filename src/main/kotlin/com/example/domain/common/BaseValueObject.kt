package com.example.domain.common

abstract class BaseValueObject {
    abstract fun getAtomicValues(): List<Any>

    override operator fun equals(other: Any?) : Boolean {
        if (other == null || other !is BaseValueObject) {
            return false
        }

        other as BaseValueObject
        val currComponents = getAtomicValues()
        val otherComponents = other.getAtomicValues()

        for (idx in currComponents.indices) {
            if (currComponents[idx] != otherComponents[idx]) {
                return false
            }
        }

        return true
    }

    override fun hashCode(): Int {
        val currComponents = getAtomicValues()
        var hash = 17
        var primeNum = 31

        for (item in currComponents) {
            hash *= 31
            hash += item.hashCode()
        }

        return hash
    }
}