package com.sopt.peekabookaos.presentation.block

import android.content.Context
import com.sopt.peekabookaos.R

data class BlockDeleteDialogContent(
    val title: String = "",
    val content: String = ""
) {
    fun deleteBlock(context: Context, follower: String): BlockDeleteDialogContent =
        BlockDeleteDialogContent(
            title = context.getString(R.string.block_dialog_delete_title, follower),
            content = context.getString(R.string.block_dialog_delete_content)
        )
}
