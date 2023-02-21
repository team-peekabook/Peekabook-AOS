package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.SearchRepository
import javax.inject.Inject

class GetSearchUserUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(nickname: String) = searchRepository.getSearchUser(nickname)
}
