package com.sopt.peekabookaos.util

sealed class UiState {
    object IDLE : UiState()
    object SUCCESS : UiState()
    object ERROR : UiState()
}
