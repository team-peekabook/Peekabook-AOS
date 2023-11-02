package com.sopt.peekabookaos.domain.entity

data class Version(
    val imageUrl: String = "",
    val iosForceVersion: String = "",
    val androidForceVersion: String = "",
    val versionText: String = ""
)