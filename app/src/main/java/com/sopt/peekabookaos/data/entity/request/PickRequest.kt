package com.sopt.peekabookaos.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class PickRequest(
    val firstPick: Int?,
    val secondPick: Int?,
    val thirdPick: Int?
)
