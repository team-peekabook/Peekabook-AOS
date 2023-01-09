package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.CreateUpdateDataSource
import javax.inject.Inject

class CreateUpdateRepositoryImpl @Inject constructor(
    private val createUpdateDataSource: CreateUpdateDataSource
) : CreateUpdateRepository
