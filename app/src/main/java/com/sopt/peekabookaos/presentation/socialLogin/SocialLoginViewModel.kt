package com.sopt.peekabookaos.presentation.socialLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.sopt.peekabookaos.domain.usecase.InitTokenUseCase
import com.sopt.peekabookaos.domain.usecase.PostLoginUseCase
import com.sopt.peekabookaos.util.KakaoLoginCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SocialLoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val initTokenUseCase: InitTokenUseCase
) : ViewModel() {
    private val _isTokenAvailability = MutableStateFlow(false)
    val isTokenAvailability = _isTokenAvailability.asStateFlow()

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
