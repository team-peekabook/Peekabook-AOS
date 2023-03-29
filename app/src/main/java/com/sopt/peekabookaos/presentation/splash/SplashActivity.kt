package com.sopt.peekabookaos.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sopt.peekabookaos.databinding.ActivitySplashBinding
import com.sopt.peekabookaos.presentation.socialLogin.SocialLoginFragment

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lottieSplash.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({ collectUiEvent() }, DURATION)
    }

    private fun collectUiEvent() {
        /** 추후 서버 로그인 로직 구현 시 분기 처리 예정 */
        startActivity(Intent(this, SocialLoginFragment::class.java))
        finish()
    }

    companion object {
        private const val DURATION: Long = 2000
    }
}
