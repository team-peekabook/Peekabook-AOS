package com.sopt.peekabookaos.presentation.login

import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.sopt.peekabookaos.util.KakaoLoginCallback
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class LoginViewModel : ViewModel() {
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

    private fun getFcmToken() {
        // TODO by 이빵주 fcm 토큰 받아오는 로직
    }

    data class LoginUiState(
        val kakaoToken: String = "",
        val fcmToken: String = "dummy fcm token",
        val isTokenAvailability: Boolean = false
    )

    companion object {
        /** 추후 서버에 보낼 로그인 타입 */
        private const val SOCIAL_TYPE = "KAKAO"
    }
}
