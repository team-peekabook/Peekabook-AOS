package com.sopt.peekabookaos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Version(
    val imageUrl: String = "",
    val androidForceVersion: String = "",
    val versionText: String = ""
) : Parcelable
