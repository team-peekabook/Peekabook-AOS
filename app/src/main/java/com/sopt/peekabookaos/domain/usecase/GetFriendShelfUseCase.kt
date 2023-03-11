package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.ShelfRepository
import javax.inject.Inject

class GetFriendShelfUseCase @Inject constructor(
    private val shelfRepository: ShelfRepository
) {
    suspend operator fun invoke(friendId: Int) = shelfRepository.getFriendShelf(friendId)
}
