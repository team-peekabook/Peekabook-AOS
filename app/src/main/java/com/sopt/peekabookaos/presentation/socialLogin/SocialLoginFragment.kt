package com.sopt.peekabookaos.presentation.socialLogin

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.service.KakaoLoginService
import com.sopt.peekabookaos.databinding.FragmentSocialLoginBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SocialLoginFragment :
    BindingFragment<FragmentSocialLoginBinding>(R.layout.fragment_social_login) {
    @Inject
    lateinit var kakaoLoginService: KakaoLoginService
    private var onBackPressedTime = 0L
    private val socialLoginViewModel: SocialLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKakaoLoginBtnClickListener()
        initTermsOfServiceClickListener()
        initPrivacyPolicyClickListener()
        collectIsTokenAvailability()
    }

//    private fun initBackPressedCallback() {
//        onBackPressedDispatcher.addCallback {
//            if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
//                onBackPressedTime = System.currentTimeMillis()
//                ToastMessageUtil.showToast(
//                    this@LoginFragment,
//                    getString(R.string.finish_app_toast_msg)
//                )
//            } else {
//                finishAffinity()
//                System.runFinalization()
//                exitProcess(0)
//            }
//        }
//    }

    private fun initKakaoLoginBtnClickListener() {
        binding.btnSocialLoginKakao.setSingleOnClickListener {
            startKakaoLogin()
        }
    }

    private fun startKakaoLogin() {
        kakaoLoginService.startKakaoLogin(socialLoginViewModel.kakaoLoginCallback)
    }

    private fun initTermsOfServiceClickListener() {
        binding.tvSocialLoginTermsOfService.setOnClickListener {
            startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse(getString(R.string.social_login_terms_of_service_link))
                )
            )
        }
    }

    private fun initPrivacyPolicyClickListener() {
        binding.tvLoginPrivacyPolicy.setOnClickListener {
            startActivity(
                Intent(ACTION_VIEW, Uri.parse(getString(R.string.social_login_privacy_policy_link)))
            )
        }
    }

    private fun collectIsTokenAvailability() {
        repeatOnStarted {
            socialLoginViewModel.uiState.collect { uiState ->
                uiState.isTokenAvailability.let { success ->
                    if (success) {
                        // TODO by 이빵주 postLogin 함수 호출 (현재 임의로 MainActivity 넣어둠)
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
