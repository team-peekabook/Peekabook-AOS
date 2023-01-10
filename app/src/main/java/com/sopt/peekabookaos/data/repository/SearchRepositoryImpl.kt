package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.SearchDataSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepository
