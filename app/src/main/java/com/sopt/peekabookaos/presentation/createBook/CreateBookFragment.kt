package com.sopt.peekabookaos.presentation.createBook

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentCreateBookBinding
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_ID
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.detail.DetailActivity
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.UiEvent
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.getParcelableCompat
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateBookFragment :
    BindingFragment<FragmentCreateBookBinding>(R.layout.fragment_create_book) {
    private val createBookViewModel: CreateBookViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = createBookViewModel
        initBookInfo()
        initEditTextClearFocus()
        initCloseBtnOnClickListener()
        collectUiEvent()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clCreateBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }

        binding.btnCreateBookSave.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }
    }

    private fun initBookInfo() {
        createBookViewModel.initBookInfo(
            arguments?.getParcelableCompat(BOOK_INFO, Book::class.java) ?: Book()
        )
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnCreateBookClose.setOnClickListener {
            activity?.finish()
        }
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            createBookViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.SUCCESS -> {
                        startActivity(
                            Intent(requireActivity(), DetailActivity::class.java).apply {
                                putExtra(BOOK_ID, createBookViewModel.bookInfo.value.id)
                            }
                        )
                        activity?.finish()
                    }
                    UiEvent.ERROR -> {
                        return@collect
                    }
                    UiEvent.IDLE -> {
                        return@collect
                    }
                }
            }
        }
    }
}
