package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.service.RecommendService
import javax.inject.Inject

data class RecommendDataSource @Inject constructor(
    private val recommendService: RecommendService
)
