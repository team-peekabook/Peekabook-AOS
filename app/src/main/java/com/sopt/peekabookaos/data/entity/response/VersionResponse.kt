package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.domain.entity.Version
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse (
    val imageUrl : String,
    val iosForceVersion : String,
    val androidForceVersion : String,
    @SerialName("text")
    val versionText : String
        ){
    fun toVersion(): Version = Version(
        imageUrl = this.imageUrl,
        iosForceVersion = this.iosForceVersion,
        androidForceVersion = this.androidForceVersion,
        versionText = this.versionText
    )
}