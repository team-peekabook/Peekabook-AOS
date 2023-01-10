package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.service.CreateUpdateService
import javax.inject.Inject

data class CreateUpdateDataSource @Inject constructor(
    private val createUpdateService: CreateUpdateService
)
