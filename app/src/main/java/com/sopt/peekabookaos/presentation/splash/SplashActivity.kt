package com.sopt.peekabookaos.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivitySplashBinding
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.entity.VersionState
import com.sopt.peekabookaos.presentation.forceUpdate.ForceUpdateActivity
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.presentation.onboarding.OnboardingActivity
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lottieSplash.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({ initIsForceUpdateObserver() }, DURATION)
    }

    private fun initIsForceUpdateObserver() {
        splashViewModel.isForceUpdate.observe(this) { success ->
            if (success) {
                splashViewModel.checkUpdateVersion()
                checkVersionUpdate()
            }
        }
    }

    private fun checkVersionUpdate() {
        when (splashViewModel.checkUpdateVersion()) {
            VersionState.LATEST -> checkSplashState()
            VersionState.OUTDATED -> {
                val intentToForceUpdate = Intent(this, ForceUpdateActivity::class.java).apply {
                    putExtra(LATEST_VERSION, splashViewModel.latestVersion.value)
                    addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(Intent(intentToForceUpdate))
                finish()
            }
        }
    }

    private fun checkSplashState() {
        when (splashViewModel.getSplashState()) {
            SplashState.ONBOARDING -> startActivity(Intent(this, OnboardingActivity::class.java))
            SplashState.MAIN -> startActivity(Intent(this, MainActivity::class.java))
        }
        overridePendingTransition(0, 0)
        finish()
    }

    companion object {
        private const val DURATION: Long = 2000
        const val LATEST_VERSION = "latest version"
    }
}
