package com.sopt.peekabookaos.presentation.block

import android.content.Context
import com.sopt.peekabookaos.R

data class BlockDialogContent(
    val title: String = "",
    val content: String = ""
) {
    fun getBlock(context: Context, follower: String): BlockDialogContent =
        BlockDialogContent(
            title = context.getString(R.string.bookshelf_dialog_block_title, follower),
            content = context.getString(R.string.bookshelf_dialog_block_content)
        )
}
