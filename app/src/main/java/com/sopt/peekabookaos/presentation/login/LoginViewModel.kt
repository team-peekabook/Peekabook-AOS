package com.sopt.peekabookaos.presentation.login

import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.sopt.peekabookaos.util.extensions.Event
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
        if (error != null) {
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Timber.e(error, "접근이 거부 됨(동의 취소)")
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Timber.e(error, "유효하지 않은 앱")
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Timber.e(error, "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Timber.e(error, "요청 파라미터 오류")
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Timber.e(error, "유효하지 않은 scope ID")
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Timber.e(error, "설정이 올바르지 않음(android key hash)")
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Timber.e(error, "서버 내부 에러")
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Timber.e(error, "앱이 요청 권한이 없음")
                }
                else -> {
                    Timber.e(error, "기타 에러")
                }
            }
        } else if (token != null) {
            Timber.e("카카오 로그인 성공 ${token.accessToken}")
            _uiState.value = _uiState.value.copy(kakaoToken = token.accessToken)
            checkTokenAvailability(_uiState.value.kakaoToken, _uiState.value.fcmToken)
        }
    }

    private fun checkTokenAvailability(kakaoToken: String, fcmToken: String) {
        val isAvailable = kakaoToken.isNotBlank() && fcmToken.isNotBlank()
        _uiState.value = _uiState.value.copy(isTokenAvailability = Event(isAvailable))
    }

    private fun getFcmToken() {
        /** fcm 토큰 받아오는 로직 */
    }

    data class LoginUiState(
        val kakaoToken: String = "",
        val fcmToken: String = "dummy fcm token",
        val isTokenAvailability: Event<Boolean> = Event(false)
    )

    companion object {
        /** 추후 서버에 보낼 로그인 타입 */
        private const val SOCIAL_TYPE = "KAKAO"
    }
}
