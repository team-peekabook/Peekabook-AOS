package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.DetailDataSource
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailDataSource: DetailDataSource
) : DetailRepository
