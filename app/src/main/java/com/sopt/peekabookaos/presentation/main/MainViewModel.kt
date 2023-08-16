package com.sopt.peekabookaos.presentation.main

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.usecase.SetSplashStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    init {
        setSplashStateUseCase(SplashState.MAIN)
    }
}
