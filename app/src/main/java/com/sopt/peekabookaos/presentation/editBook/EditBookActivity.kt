package com.sopt.peekabookaos.presentation.editBook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityEditBookBinding
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.BookDetail
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_COMMENT
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.UiEvent
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.getParcelable
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBookActivity :
    BindingActivity<ActivityEditBookBinding>(R.layout.activity_edit_book) {
    private val editBookViewModel: EditBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = editBookViewModel
        setPreviousBook()
        initEditTextClearFocus()
        initCloseBtnOnClickListener()
        collectUiEvent()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clEditBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }

        binding.btnEditBookSave.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }
    }

    private fun setPreviousBook() {
        editBookViewModel.setPreviousBook(
            bookInfo = intent.getParcelable(BOOK_INFO, Book::class.java) ?: Book(),
            bookDetail = intent.getParcelable(BOOK_COMMENT, BookDetail::class.java)
                ?: BookDetail()
        )
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnEditBookClose.setOnClickListener {
            finish()
        }
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            editBookViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.IDLE -> {
                        binding.btnEditBookSave.isEnabled = false
                    }
                    UiEvent.SUCCESS -> {
                        finish()
                    }
                    UiEvent.ERROR -> {
                        binding.btnEditBookSave.isEnabled = true
                    }
                }
            }
        }
    }
}
