package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.FriendList

interface BlockRepository {
    suspend fun getBlock(): Result<List<FriendList>>
}
