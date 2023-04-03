package com.sopt.peekabookaos.presentation.withdraw

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityWithdrawBinding
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
        collectIsWithdraw()
    }

    private fun initBackBtnOnClickListener() {
        binding.btnWithdrawBack.setOnClickListener {
            finish()
        }
    }

    private fun collectIsWithdraw() {
        repeatOnStarted {
            withDrawViewModel.isWithdraw.collect { success ->
                if (success) {
                    WithdrawDialog().show(supportFragmentManager, WithdrawDialog.TAG)
                }
            }
        }
    }
}
