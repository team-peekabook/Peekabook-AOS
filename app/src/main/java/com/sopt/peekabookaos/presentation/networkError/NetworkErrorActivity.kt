package com.sopt.peekabookaos.presentation.networkError

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityNetworkErrorBinding
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.presentation.onboarding.OnboardingActivity
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.activityTransition
import com.sopt.peekabookaos.util.extensions.isNetworkConnected
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class NetworkErrorActivity :
    BindingActivity<ActivityNetworkErrorBinding>(R.layout.activity_network_error) {
    private val networkErrorViewModel: NetworkErrorViewModel by viewModels()
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetworkConnect()
        initBackPressedCallback()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                onBackPressedTime = System.currentTimeMillis()
                ToastMessageUtil.showToast(
                    this@NetworkErrorActivity,
                    getString(R.string.finish_app_toast_msg)
                )
            } else {
                finishAffinity()
                System.runFinalization()
                exitProcess(0)
            }
        }
    }

    private fun checkNetworkConnect() {
        binding.btnNetworkErrorRetry.setOnClickListener {
            if (isNetworkConnected()) {
                when (networkErrorViewModel.getSplashState()) {
                    SplashState.ONBOARDING -> startActivity(
                        Intent(this, OnboardingActivity::class.java)
                    )

                    SplashState.MAIN -> startActivity(Intent(this, MainActivity::class.java))
                }
                activityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0)
                finish()
            } else {
                ToastMessageUtil.showToast(
                    this@NetworkErrorActivity,
                    getString(R.string.network_error_toast)
                )
            }
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}
