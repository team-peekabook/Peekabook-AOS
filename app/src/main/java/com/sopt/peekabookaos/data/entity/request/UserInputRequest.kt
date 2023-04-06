package com.sopt.peekabookaos.data.entity.request

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody

@Serializable
data class UserInputRequest(
    @Contextual
    val profileImage: MultipartBody.Part,
    val nickname: String,
    val intro: String
)
