package com.sopt.peekabookaos.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
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
        initSearchBookAdapter()
        initEditTextClearFocus()
        collectUiState()
    }

    private fun initSearchBookAdapter() {
        binding.rvSearchBook.adapter = searchBookAdapter
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

    private fun collectUiState() {
        repeatOnStarted {
            searchBookViewModel.uiState.collect { uiState ->
                uiState.onSuccess { result ->
                    searchBookAdapter.submitList(result)
                }.onFailed {
                    /* empty뷰 띄우기 */
                }
            }
        }
    }
}
