package com.sopt.peekabookaos.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.sopt.peekabookaos.BuildConfig
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivitySplashBinding
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.presentation.forceUpdate.ForceUpdateActivity
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.presentation.onboarding.OnboardingActivity
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashViewModel: SplashViewModel by viewModels()
    private val appVersionName = BuildConfig.VERSION_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lottieSplash.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({ initObserver() }, DURATION)
    }

    private fun initObserver() {
        splashViewModel.isVersionStatus.observe(this) { success ->
            if (success) {
                splashViewModel.getSplitVersion()
                checkVersionUpdate()
            }
        }
    }

    private fun checkVersionUpdate() {
        val appVersionList = appVersionName.split(".")
        val previousMajor = appVersionList[0]
        val previousMinor = appVersionList[1]
        val isPreviousVersions =
            previousMajor != splashViewModel.majorVersion || previousMinor != splashViewModel.minorVersion
        if (isPreviousVersions) {
            startActivity(Intent(this, ForceUpdateActivity::class.java))
            finish()
        } else {
            checkSplashState()
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
    }
}
