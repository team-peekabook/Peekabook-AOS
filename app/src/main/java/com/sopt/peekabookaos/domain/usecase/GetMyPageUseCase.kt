package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.MyPageRepository
import javax.inject.Inject

class GetMyPageUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke() = myPageRepository.getMyPage()
}
