package com.sopt.peekabookaos.util.extensions

sealed class UiState {
    object IDLE : UiState()
    object SUCCESS : UiState()
    object ERROR : UiState()
}
