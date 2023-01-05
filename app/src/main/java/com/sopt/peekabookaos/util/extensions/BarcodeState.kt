package com.sopt.peekabookaos.util.extensions

sealed class BarcodeState {
    object Default : BarcodeState()
    object Success : BarcodeState()
    object Fail : BarcodeState()
}
