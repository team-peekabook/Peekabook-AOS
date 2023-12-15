package com.sopt.peekabookaos.domain.entity

sealed class SplashUiState {
    data object Idle : SplashUiState()
    data object Error : SplashUiState()
    data object ShowSplash : SplashUiState()
    data object CanStartOnboarding : SplashUiState()
    data object CanStartMain : SplashUiState()
    data class ForceUpdate(val data: UpdateInformation) : SplashUiState()
}
