package com.sopt.peekabookaos.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivitySplashBinding
import com.sopt.peekabookaos.domain.entity.SplashUiState
import com.sopt.peekabookaos.domain.entity.UpdateInformation
import com.sopt.peekabookaos.presentation.forcedUpdate.ForcedUpdateActivity
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.presentation.networkError.NetworkErrorActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.networkError.NetworkErrorActivity.Companion.NETWORK_ERROR
import com.sopt.peekabookaos.presentation.onboarding.OnboardingActivity
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.activityOpenTransition
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLocation()
    }

    private fun checkLocation() {
        when (intent.getStringExtra(LOCATION)) {
            NETWORK_ERROR -> {
                collectUiState()
            }

            else -> {
                binding.lottieSplash.playAnimation()
                Handler(Looper.getMainLooper()).postDelayed({ collectUiState() }, DURATION)
            }
        }
    }

    private fun collectUiState() {
        repeatOnStarted {
            splashViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is SplashUiState.Idle -> {}
                    is SplashUiState.Error -> {}
                    is SplashUiState.ShowSplash -> {}
                    is SplashUiState.CanStartOnboarding -> startOnboardingActivity()
                    is SplashUiState.CanStartMain -> startMainActivity()
                    is SplashUiState.ForceUpdate -> {
                        startForcedUpdateActivity(uiState.data)
                    }
                }
            }
        }
    }

    private fun startForcedUpdateActivity(updateInformation: UpdateInformation) {
        Intent(this, ForcedUpdateActivity::class.java).apply {
            putExtra(UPDATE_INFORMATION, updateInformation)
        }.also {
            startActivityWithAnimation(it)
        }
    }

    private fun startOnboardingActivity() {
        Intent(this, OnboardingActivity::class.java).also { startActivityWithAnimation(it) }
    }

    private fun startMainActivity() {
        Intent(this, MainActivity::class.java).also { startActivityWithAnimation(it) }
    }

    private fun startActivityWithAnimation(intent: Intent) {
        startActivity(intent)
        activityOpenTransition(0, 0)
        finishAffinity()
    }

    companion object {
        private const val DURATION: Long = 2000L
        const val UPDATE_INFORMATION = "update information"
    }
}
