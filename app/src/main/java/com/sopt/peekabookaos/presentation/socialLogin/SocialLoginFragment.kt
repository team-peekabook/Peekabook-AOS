package com.sopt.peekabookaos.presentation.socialLogin

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.service.KakaoLoginService
import com.sopt.peekabookaos.databinding.FragmentSocialLoginBinding
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class SocialLoginFragment :
    BindingFragment<FragmentSocialLoginBinding>(R.layout.fragment_social_login) {
    @Inject
    lateinit var kakaoLoginService: KakaoLoginService
    private var onBackPressedTime = 0L
    private val socialLoginViewModel: SocialLoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackPressedCallback()
        initKakaoLoginBtnClickListener()
        initTermsOfServiceClickListener()
        initPrivacyPolicyClickListener()
        collectIsTokenAvailability()
        collectIsSignedUp()
    }

    private fun initBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val curTime = System.currentTimeMillis()
                    val gap = curTime - onBackPressedTime
                    if (gap > WAITING_DEADLINE) {
                        onBackPressedTime = curTime
                        ToastMessageUtil.showToast(
                            requireContext(),
                            getString(R.string.finish_app_toast_msg)
                        )
                        return
                    }
                    finishAffinity(requireActivity())
                    System.runFinalization()
                    exitProcess(0)
                }
            }
        )
    }

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
            socialLoginViewModel.isKakaoLogin.collect { success ->
                if (success) {
                    socialLoginViewModel.postLogin()
                } else {
                    Timber.e("카카오 로그인 실패")
                }
            }
        }
    }

    private fun collectIsSignedUp() {
        repeatOnStarted {
            socialLoginViewModel.isSignedUp.collect { signedUp ->
                if (signedUp) {
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                } else {
                    findNavController().navigate(R.id.action_socialLoginFragment_to_userInputFragment)
                }
            }
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}
