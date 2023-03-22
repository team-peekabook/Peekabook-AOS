package com.sopt.peekabookaos.data.entity

import com.sopt.peekabookaos.domain.entity.Recommend
import kotlinx.serialization.Serializable

@Serializable
data class RecommendEntity(
    val recommendId: Int,
    val recommendDesc: String?,
    val createdAt: String,
    val friendId: Int,
    val friendNickname: String,
    val friendImage: String,
    val bookId: Int,
    val bookTitle: String,
    val author: String,
    val bookImage: String
) {
    fun toRecommend(): Recommend = Recommend(
        recommendId = this.recommendId,
        recommendDesc = this.recommendDesc,
        createdAt = this.createdAt,
        friendId = this.recommendId,
        friendNickname = this.friendNickname,
        friendImage = this.friendImage,
        bookId = this.bookId,
        bookTitle = this.bookTitle,
        author = this.author,
        bookImage = this.bookImage
    )
}
