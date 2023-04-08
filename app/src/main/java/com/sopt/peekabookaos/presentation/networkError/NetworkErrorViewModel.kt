package com.sopt.peekabookaos.presentation.networkError

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.usecase.GetSplashStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkErrorViewModel @Inject constructor(
    private val getSplashStateUseCase: GetSplashStateUseCase
) : ViewModel() {
    fun getSplashState(): SplashState = getSplashStateUseCase()
}
