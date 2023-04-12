package com.sopt.peekabookaos.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class ReportRequest(
    val reasonIndex: Int,
    val etc: String?
)
