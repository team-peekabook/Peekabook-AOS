package com.sopt.peekabookaos.presentation.socialLogin

import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.sopt.peekabookaos.util.KakaoLoginCallback
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class SocialLoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    /** 회원가입 여부 확인하는 변수 (추후 사용 예정) */
    private val _isSignedUp = MutableSharedFlow<Boolean>()
    val isSignedUp = _isSignedUp.asSharedFlow()

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        KakaoLoginCallback(_uiState) { checkTokenAvailability() }.handleResult(token, error)
    }

    private fun checkTokenAvailability() {
        val isAvailable =
            _uiState.value.kakaoToken.isNotBlank() && _uiState.value.fcmToken.isNotBlank()
        _uiState.value = _uiState.value.copy(isTokenAvailability = isAvailable)
    }

    data class LoginUiState(
        val kakaoToken: String = "",
        val fcmToken: String = "dummy fcm token",
        val isTokenAvailability: Boolean = false
    )

    companion object {
        private const val SOCIAL_TYPE = "KAKAO"
    }
}