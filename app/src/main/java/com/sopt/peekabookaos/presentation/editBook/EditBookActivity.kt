package com.sopt.peekabookaos.presentation.editBook

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.LayoutCreateUpdateBookBinding
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.BookComment
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_COMMENT
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.UPDATE
import com.sopt.peekabookaos.presentation.detail.DetailActivity
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.KeyBoardUtil
import com.sopt.peekabookaos.util.extensions.getParcelable
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBookActivity :
    BindingActivity<LayoutCreateUpdateBookBinding>(R.layout.layout_create_update_book) {
    private val editBookViewModel: EditBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = editBookViewModel
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
                editBookViewModel.initUiState(
                    bookData = intent.getParcelable(BOOK, Book::class.java)!!,
                    bookComment = intent.getParcelable(BOOK_COMMENT, BookComment::class.java)!!,
                    update = true
                )
            }
            else -> {
                editBookViewModel.initUiState(
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
            editBookViewModel.isPatch.collect { success ->
                if (success) {
                    /** 수정 성공시 로직 */
                }
            }
        }
    }

    private fun isPostCollect() {
        repeatOnStarted {
            editBookViewModel.isPost.collect { success ->
                if (success) {
                    val toDetail = Intent(this@EditBookActivity, DetailActivity::class.java)
                    toDetail.putExtra(
                        BOOK_INFO,
                        editBookViewModel.uiState.value.bookData.id
                    )
                    finish()
                    startActivity(toDetail)
                }
            }
        }
    }
}