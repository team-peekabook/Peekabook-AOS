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
        KakaoLoginCallback { accessToken ->
            _isTokenAvailability.value = true
            initTokenUseCase(accessToken = accessToken, refreshToken = "")
        }.handleResult(token, error)
    }

    fun postLogin() {
        viewModelScope.launch {
            postLoginUseCase(SOCIAL_TYPE)
                .onSuccess { response ->
                    initTokenUseCase(response.accessToken, response.refreshToken)
                    _isSignedUp.emit(response.isSignedUp)
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    companion object {
        private const val SOCIAL_TYPE = "kakao"
    }
}
