package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.ShelfDataSource
import com.sopt.peekabookaos.domain.entity.FriendShelf
import com.sopt.peekabookaos.domain.entity.MyShelf
import com.sopt.peekabookaos.domain.entity.Picks
import com.sopt.peekabookaos.domain.repository.ShelfRepository
import javax.inject.Inject

class ShelfRepositoryImpl @Inject constructor(
    private val shelfDataSource: ShelfDataSource
) : ShelfRepository {
    override suspend fun getFriendShelf(friendId: Int): Result<FriendShelf> =
        kotlin.runCatching { shelfDataSource.getFriendShelf(friendId) }.map { response ->
            requireNotNull(response.data).toFriendShelf()
        }

    override suspend fun getMyShelf(): Result<MyShelf> =
        kotlin.runCatching { shelfDataSource.getMyShelf() }.map { response ->
            requireNotNull(response.data).toMyShelf()
        }

    override suspend fun getPick(): Result<List<Picks>> =
        kotlin.runCatching { shelfDataSource.getPick() }.map { response ->
            requireNotNull(response.data).map { picksEntity ->
                picksEntity.toPicks()
            }
        }

    override suspend fun patchPick(
        firstPick: Int?,
        secondPick: Int?,
        thirdPick: Int?
    ): Result<Boolean> =
        kotlin.runCatching { shelfDataSource.patchPick(firstPick, secondPick, thirdPick) }
            .map { response -> response.success }
}
