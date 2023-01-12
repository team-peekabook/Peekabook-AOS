package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.NaverDataSource
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val naverDataSource: NaverDataSource
) : NaverRepository {
}
