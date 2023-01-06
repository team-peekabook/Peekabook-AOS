package com.sopt.peekabookaos.presentation.barcodeScanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.util.extensions.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BarcodeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Book>>(UiState.IDLE)
    val uiState: StateFlow<UiState<Book>> = _uiState.asStateFlow()

    /* 서버 통신 더미 변수 (추후 제거 예정) */
    private val serverStatus = true

    fun postBarcode(barcodeString: String) {
        /* 서버 통신 시 구현 예정*/
        viewModelScope.launch {
            _uiState.emit(UiState.IDLE)
            if (serverStatus) {
                _uiState.emit(UiState.Success<Book>(Book()))
            } else {
                _uiState.emit(UiState.Error(Throwable()))
            }
        }
    }
}
