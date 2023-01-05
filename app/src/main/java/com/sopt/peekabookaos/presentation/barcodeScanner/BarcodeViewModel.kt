package com.sopt.peekabookaos.presentation.barcodeScanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.util.extensions.BarcodeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BarcodeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<BarcodeState>(BarcodeState.Default)
    val uiState: StateFlow<BarcodeState> = _uiState.asStateFlow()

    /* 서버 통신 더미 변수 (추후 제거 예정) */
    private val serverStatus = true

    fun postBarcode(barcodeString: String) {
        /* 서버 통신 시 구현 예정*/
        viewModelScope.launch {
            _uiState.emit(BarcodeState.Default)
            if (serverStatus) {
                _uiState.emit(BarcodeState.Success)
            } else {
                _uiState.emit(BarcodeState.Fail)
            }
        }
    }
}
