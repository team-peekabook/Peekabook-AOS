package com.sopt.peekabookaos.presentation.withdraw

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityWithdrawBinding
import com.sopt.peekabookaos.util.UiEvent
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawActivity : BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {
    private val withDrawViewModel: WithdrawViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = withDrawViewModel
        initBackBtnOnClickListener()
        collectUiEvent()
    }

    private fun initBackBtnOnClickListener() {
        binding.btnWithdrawBack.setOnClickListener {
            finish()
        }
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            withDrawViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.IDLE -> {
                        binding.btnWithdrawConfirm.isEnabled = false
                    }
                    UiEvent.SUCCESS -> {
                        WithdrawDialog().show(supportFragmentManager, WithdrawDialog.TAG)
                        binding.btnWithdrawConfirm.isEnabled = true
                    }
                    UiEvent.ERROR -> {
                        binding.btnWithdrawConfirm.isEnabled = true
                    }
                }
            }
        }
    }
}
