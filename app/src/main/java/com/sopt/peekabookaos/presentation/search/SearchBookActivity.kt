package com.sopt.peekabookaos.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivitySearchBookBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.KeyBoardUtil
import com.sopt.peekabookaos.util.extensions.onFailed
import com.sopt.peekabookaos.util.extensions.onSuccess
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBookActivity :
    BindingActivity<ActivitySearchBookBinding>(R.layout.activity_search_book) {
    private val searchBookViewModel: SearchBookViewModel by viewModels()
    private val searchBookAdapter = SearchBookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = searchBookViewModel
        initViewVisibility()
        initSearchBookAdapter()
        initEditTextClearFocus()
        initKeyboardDoneClickListener()
        collectUiState()
    }

    private fun initSearchBookAdapter() {
        binding.rvSearchBook.adapter = searchBookAdapter
    }

    private fun initViewVisibility() {
        with(binding) {
            ivSearchBookError.visibility = View.INVISIBLE
            tvSearchBookError.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clSearchBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }

        binding.rvSearchBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }

        binding.btnSearchBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }
    }

    private fun initKeyboardDoneClickListener() {
        binding.etSearchBook.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnSearchBook.performClick()
                handled = true
            }
            KeyBoardUtil.hide(activity = this)
            handled
        }
    }

    private fun collectUiState() {
        repeatOnStarted {
            searchBookViewModel.uiState.collect { uiState ->
                uiState.onSuccess { result ->
                    with(binding) {
                        ivSearchBookError.visibility = View.INVISIBLE
                        tvSearchBookError.visibility = View.INVISIBLE
                        rvSearchBook.visibility = View.VISIBLE
                    }
                    searchBookAdapter.submitList(result)
                }.onFailed {
                    with(binding) {
                        ivSearchBookError.visibility = View.VISIBLE
                        tvSearchBookError.visibility = View.VISIBLE
                        rvSearchBook.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}
