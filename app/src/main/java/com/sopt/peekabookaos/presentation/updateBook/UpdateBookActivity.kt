package com.sopt.peekabookaos.presentation.updateBook

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.BookComment
import com.sopt.peekabookaos.databinding.LayoutCreateUpdateBookBinding
import com.sopt.peekabookaos.presentation.detail.DetailActivity
import com.sopt.peekabookaos.presentation.detail.DetailActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.search.book.SearchBookActivity
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.KeyBoardUtil
import com.sopt.peekabookaos.util.extensions.getParcelable
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateBookActivity :
    BindingActivity<LayoutCreateUpdateBookBinding>(R.layout.layout_create_update_book) {
    private val updateBookViewModel: UpdateBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = updateBookViewModel
        initUiState()
        initEditTextClearFocus()
        initCloseBtnOnClickListener()
        isPatchCollect()
        isPostCollect()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clCreateUpdate.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }

        binding.btnCreateUpdateBookSave.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }
    }

    private fun initUiState() {
        when (intent.getStringExtra(LOCATION)) {
            UPDATE -> {
                updateBookViewModel.initUiState(
                    bookData = intent.getParcelable(BOOK, Book::class.java)!!,
                    bookComment = intent.getParcelable(BOOK_COMMENT, BookComment::class.java)!!,
                    update = true
                )
            }
            else -> {
                updateBookViewModel.initUiState(
                    bookData = intent.getParcelable(BOOK, Book::class.java)!!,
                    bookComment = BookComment("", ""),
                    update = false
                )
            }
        }
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnCreateUpdateBookClose.setSingleOnClickListener {
            finish()
        }
    }

    private fun isPatchCollect() {
        repeatOnStarted {
            updateBookViewModel.isPatch.collect { success ->
                if (success) {
                    val activity = SearchBookActivity()
                    activity.finish()
                    finish()
                }
            }
        }
    }

    private fun isPostCollect() {
        repeatOnStarted {
            updateBookViewModel.isPost.collect { success ->
                if (success) {
                    val toDetail = Intent(this@UpdateBookActivity, DetailActivity::class.java)
                    toDetail.putExtra(
                        BOOK_INFO,
                        updateBookViewModel.uiState.value.bookData.id
                    )
                    finish()
                    startActivity(toDetail)
                }
            }
        }
    }

    companion object {
        const val LOCATION = "location"
        const val UPDATE = "update"
        const val CREATE = "create"
        const val BOOK = "book"
        const val BOOK_COMMENT = "book_comment"
    }
}