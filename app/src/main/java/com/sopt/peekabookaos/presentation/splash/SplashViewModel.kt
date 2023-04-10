package com.sopt.peekabookaos.presentation.splash

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.usecase.GetSplashStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSplashStateUseCase: GetSplashStateUseCase
) : ViewModel() {
    fun getSplashState(): SplashState = getSplashStateUseCase()
}
