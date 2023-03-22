package com.sopt.peekabookaos.presentation.login

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.service.KakaoLoginService
import com.sopt.peekabookaos.databinding.ActivityLoginBinding
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    @Inject
    lateinit var kakaoLoginService: KakaoLoginService
    private val loginViewModel: LoginViewModel by viewModels()
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackPressedCallback()
        initKakaoLoginBtnClickListener()
        initTermsOfServiceClickListener()
        initPrivacyPolicyClickListener()
        collectIsTokenAvailability()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                onBackPressedTime = System.currentTimeMillis()
                ToastMessageUtil.showToast(
                    this@LoginActivity,
                    getString(R.string.finish_app_toast_msg)
                )
            } else {
                finishAffinity()
                System.runFinalization()
                exitProcess(0)
            }
        }
    }

    private fun initKakaoLoginBtnClickListener() {
        binding.btnKakaoLogin.setSingleOnClickListener {
            startKakaoLogin()
        }
    }

    private fun startKakaoLogin() {
        kakaoLoginService.startKakaoLogin(loginViewModel.kakaoLoginCallback)
    }

    private fun initTermsOfServiceClickListener() {
        binding.tvLoginTermsOfService.setOnClickListener {
            startActivity(
                Intent(ACTION_VIEW, Uri.parse(getString(R.string.login_terms_of_service_link)))
            )
        }
    }

    private fun initPrivacyPolicyClickListener() {
        binding.tvLoginPrivacyPolicy.setOnClickListener {
            startActivity(
                Intent(ACTION_VIEW, Uri.parse(getString(R.string.login_privacy_policy_link)))
            )
        }
    }

    private fun collectIsTokenAvailability() {
        repeatOnStarted {
            loginViewModel.uiState.collect { uiState ->
                uiState.isTokenAvailability.let { success ->
                    if (success) {
                        // TODO by 이빵주 postLogin 함수 호출 (현재 임의로 MainActivity 넣어둠)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Timber.e("Token is not available")
                    }
                }
            }
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}
