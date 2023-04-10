package com.sopt.peekabookaos.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityOnboardingBinding
import com.sopt.peekabookaos.domain.entity.Onboarding
import com.sopt.peekabookaos.presentation.login.LoginActivity
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import kotlin.system.exitProcess

class OnboardingActivity :
    BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    private val onboardingAdapter: OnboardingAdapter?
        get() = binding.vpOnboarding.adapter as? OnboardingAdapter
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initNextBtnClickListener()
        initBackPressedCallback()
    }

    private fun initAdapter() {
        if (binding.vpOnboarding.adapter == null) {
            binding.vpOnboarding.adapter = OnboardingAdapter()
        }
        binding.vpOnboarding.adapter = onboardingAdapter
        onboardingAdapter?.submitList(onboardingList)

        TabLayoutMediator(binding.tlOnboardingIndex, binding.vpOnboarding) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }

    private fun initNextBtnClickListener() {
        binding.tvOnboarding.setSingleOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                onBackPressedTime = System.currentTimeMillis()
                ToastMessageUtil.showToast(
                    this@OnboardingActivity,
                    getString(R.string.finish_app_toast_msg)
                )
            } else {
                finishAffinity()
                System.runFinalization()
                exitProcess(0)
            }
        }
    }

    companion object {
        private val onboardingList = listOf(
            Onboarding(R.drawable.ic_onboarding_1, R.drawable.ic_onboarding_content_1),
            Onboarding(R.drawable.ic_onboarding_2, R.drawable.ic_onboarding_content_2),
            Onboarding(R.drawable.ic_onboarding_3, R.drawable.ic_onboarding_content_3),
            Onboarding(R.drawable.ic_onboarding_4, R.drawable.ic_onboarding_content_4)
        )
        private const val WAITING_DEADLINE = 2000L
    }
}
