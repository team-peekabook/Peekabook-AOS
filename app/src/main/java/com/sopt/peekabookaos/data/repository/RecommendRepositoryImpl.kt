package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.RecommendDataSource
import javax.inject.Inject

class RecommendRepositoryImpl @Inject constructor(
    private val recommendDataSource: RecommendDataSource
) : RecommendRepository
