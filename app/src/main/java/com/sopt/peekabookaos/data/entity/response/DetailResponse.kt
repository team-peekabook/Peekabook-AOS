package com.sopt.peekabookaos.data.entity.response

import com.sopt.peekabookaos.data.entity.Detail
import kotlinx.serialization.Serializable

@Serializable
data class DetailResponse(
    val detailBook: List<Detail>
)
