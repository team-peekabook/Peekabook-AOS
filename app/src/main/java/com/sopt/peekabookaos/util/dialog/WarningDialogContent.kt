package com.sopt.peekabookaos.util.dialog

import android.content.Context
import com.sopt.peekabookaos.R

data class WarningDialogContent(
    val title: String = "",
    val cancel: String = "",
    val confirm: String = ""
) {
    fun getWarningDeleteBook(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_dialog_delete_book_title),
            cancel = context.getString(R.string.warning_dialog_cancel),
            confirm = context.getString(R.string.warning_dialog_delete)
        )

    fun getWarningRecommendBook(context: Context, follower: String): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_dialog_recommend_title, follower),
            cancel = context.getString(R.string.warning_dialog_cancel),
            confirm = context.getString(R.string.warning_dialog_recommend)
        )

    fun getWarningDeleteFollow(context: Context, follower: String): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_dialog_delete_follower_title, follower),
            cancel = context.getString(R.string.warning_dialog_cancel),
            confirm = context.getString(R.string.warning_dialog_delete)
        )
    fun getWarningUnfollow(context: Context, follower: String): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_dialog_unfollow_title, follower),
            cancel = context.getString(R.string.warning_dialog_cancel),
            confirm = context.getString(R.string.warning_dialog_unfollow)
        )
    fun getWarningBlock(context: Context, follower: String): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_dialog_block_title, follower),
            cancel = context.getString(R.string.warning_dialog_cancel),
            confirm = context.getString(R.string.warning_dialog_block)
        )
}
