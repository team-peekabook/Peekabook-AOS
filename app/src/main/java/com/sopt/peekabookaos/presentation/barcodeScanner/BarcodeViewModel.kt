package com.sopt.peekabookaos.presentation.barcodeScanner

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.util.extensions.BarcodeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BarcodeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<BarcodeState>(BarcodeState.Default)
    val uiState: StateFlow<BarcodeState> = _uiState.asStateFlow()


        /* 서버 통신 시 구현 예정*/
        _uiState.value = true
    }
}
