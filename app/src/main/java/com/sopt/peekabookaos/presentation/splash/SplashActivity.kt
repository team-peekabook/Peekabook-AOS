package com.sopt.peekabookaos.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import com.sopt.peekabookaos.BuildConfig
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivitySplashBinding
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.presentation.forceUpdate.ForceUpdateActivity
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.presentation.onboarding.OnboardingActivity
import com.sopt.peekabookaos.util.ToastMessageUtil.showToast
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashViewModel: SplashViewModel by viewModels()
    private val appVersionName = BuildConfig.VERSION_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataObserver()
        binding.lottieSplash.playAnimation()
        splashViewModel.getVersion()
    }

    private fun initDataObserver() {
        splashViewModel.isVersionStatus.observe(this){ success ->
            if (success) {
                checkVersionUpdate()
            }
        }
    }

    private fun checkVersionUpdate() {
        val isPreviousVersion = appVersionName != splashViewModel.versionName.value
        if (isPreviousVersion) {
            startActivity(Intent(this, ForceUpdateActivity::class.java))
            finish()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({ checkSplashState() }, DURATION)
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
