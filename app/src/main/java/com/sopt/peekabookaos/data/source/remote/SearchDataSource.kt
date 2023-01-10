package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.service.SearchService
import javax.inject.Inject

data class SearchDataSource @Inject constructor(
    private val searchService: SearchService
)
