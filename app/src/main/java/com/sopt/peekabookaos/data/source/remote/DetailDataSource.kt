package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.service.DetailService
import javax.inject.Inject

data class DetailDataSource @Inject constructor(
    private val detailService: DetailService
)
