package com.sopt.peekabookaos.presentation.search.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivitySearchUserBinding
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.UiState
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserActivity :
    BindingActivity<ActivitySearchUserBinding>(R.layout.activity_search_user) {
    private val searchUserViewModel: SearchUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = searchUserViewModel
        initEditTextClearFocus()
        initKeyboardDoneClickListener()
        initCloseBtnClickListener()
        collectSearchState()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clSearchUser.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }

        binding.btnSearchUser.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }
    }

    private fun initKeyboardDoneClickListener() {
        binding.etSearchUser.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnSearchUser.performClick()
                handled = true
            }
            KeyBoardUtil.hide(activity = this)
            handled
        }
    }

    private fun initCloseBtnClickListener() {
        binding.btnSearchUserClose.setSingleOnClickListener {
            finish()
        }
    }

    private fun collectSearchState() {
        repeatOnStarted {
            searchUserViewModel.searchState.collect { uiState ->
                when (uiState) {
                    UiState.SUCCESS -> {
                        with(binding) {
                            clSearchUserProfile.visibility = View.VISIBLE
                            llSearchUserError.visibility = View.INVISIBLE
                        }
                    }
                    UiState.ERROR -> {
                        with(binding) {
                            clSearchUserProfile.visibility = View.INVISIBLE
                            llSearchUserError.visibility = View.VISIBLE
                        }
                    }
                    UiState.IDLE -> {
                        return@collect
                    }
                }
            }
        }
    }
}
