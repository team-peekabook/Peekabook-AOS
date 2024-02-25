package com.sopt.peekabookaos.presentation.recommend

import android.content.Context
import com.sopt.peekabookaos.R

data class RecommendDeleteDialogContent(
    val title: String = "",
    val content: String = ""
) {
    fun getRecommended(context: Context): RecommendDeleteDialogContent =
        RecommendDeleteDialogContent(
            title = context.getString(R.string.recommend_recommended_delete_dialog_title),
            content = context.getString(R.string.recommend_recommended_delete_dialog_content)
        )

    fun getRecommending(context: Context): RecommendDeleteDialogContent =
        RecommendDeleteDialogContent(
            title = context.getString(R.string.recommend_recommending_delete_dialog_title),
            content = context.getString(R.string.recommend_recommending_delete_dialog_content)
        )
}
