package com.sopt.peekabookaos.presentation.barcodeScanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class BarcodeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(Book())
    val uiState = _uiState.asStateFlow()

    private val _serverStatus = MutableStateFlow<BarcodeState>(BarcodeState.IDLE)
    val serverStatus = _serverStatus.asStateFlow()

    /* 서버 통신 더미 변수 (추후 제거 예정) */
    private val serverDummyStatus = true

    fun postBarcode(barcodeString: String) {
        /* 서버 통신 시 구현 예정*/
        viewModelScope.launch {
            _serverStatus.emit(BarcodeState.IDLE)
            if (serverDummyStatus) {
                _serverStatus.emit(BarcodeState.SUCCESS)
                Timber.e("asdf emit SUCCESS serverStatus = ${_serverStatus.value}")
                _uiState.value = dummy
            } else {
                _serverStatus.emit(BarcodeState.ERROR)
                Timber.e("asdf emit ERROR serverStatus = ${_serverStatus.value}")
            }
        }
    }

    fun initServerStatus() {
        _serverStatus.value = BarcodeState.IDLE
    }

    companion object {
        private val dummy = Book(
            bookImage = "http://image.yes24.com/goods/90365124/XL",
            bookTitle = "아무튼, 여름",
            author = "김신회",
            description = "",
            memo = ""
        )
    }
}

sealed class BarcodeState {
    object IDLE : BarcodeState()
    object SUCCESS : BarcodeState()
    object ERROR : BarcodeState()
}
