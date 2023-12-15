package com.sopt.peekabookaos.domain.entity

class Version private constructor(
    private val major: Int,
    private val minor: Int
) {

    fun greaterThan(other: Version): Boolean = other.major < major || other.minor < minor

    companion object {
        private const val VERSION_SPLIT_SIGN = "."
        private const val MAJOR_POSITION = 0
        private const val MINOR_POSITION = 1
        fun of(version: String) = version.split(VERSION_SPLIT_SIGN).map {
            it.toInt()
        }.let {
            Version(it[MAJOR_POSITION], it[MINOR_POSITION])
        }
    }
}
