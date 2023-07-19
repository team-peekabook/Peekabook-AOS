package com.sopt.peekabookaos.presentation.search.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivitySearchUserBinding
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.UiEvent
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
        initSearchFocus()
        initEditTextClearFocus()
        initKeyboardDoneClickListener()
        initCloseBtnClickListener()
        collectSearchState()
    }

    private fun initSearchFocus() {
        binding.etSearchUser.requestFocus()
        KeyBoardUtil.show(this)
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
            searchUserViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.IDLE -> {
                        binding.btnSearchUser.isEnabled = false
                    }
                    UiEvent.SUCCESS -> {
                        with(binding) {
                            clSearchUserProfile.isVisible = true
                            llSearchUserError.isVisible = false
                            binding.btnSearchUser.isEnabled = true
                        }
                    }
                    UiEvent.ERROR -> {
                        with(binding) {
                            clSearchUserProfile.isVisible = false
                            llSearchUserError.isVisible = true
                            binding.btnSearchUser.isEnabled = true
                        }
                    }
                }
            }
        }
    }
}
