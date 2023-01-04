package com.sopt.peekabookaos.presentation.barcodeScanner

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BarcodeViewModel : ViewModel() {
    private val barcode = MutableStateFlow("")

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState.asStateFlow()

    fun initBarcode(barcodeString: String) {
        barcode.value = barcodeString
        postBarcode()
    }

    private fun postBarcode() {
        /* 서버 통신 시 구현 예정*/
        _uiState.value = true
    }
}
