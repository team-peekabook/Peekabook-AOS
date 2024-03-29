package com.sopt.peekabookaos.presentation.barcodeScanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.usecase.GetBookToBarcodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    private val getBookToBarcodeUseCase: GetBookToBarcodeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Book>())
    val uiState = _uiState.asStateFlow()

    private val _barcodeState = MutableStateFlow<BarcodeState>(BarcodeState.IDLE)
    val barcodeState = _barcodeState.asStateFlow()

    fun postBarcode(barcodeString: String) {
        viewModelScope.launch {
            _barcodeState.emit(BarcodeState.IDLE)
            getBookToBarcodeUseCase(barcodeString)
                .onSuccess { response ->
                    if (response.isEmpty()) {
                        _barcodeState.emit(BarcodeState.ERROR)
                    } else {
                        _uiState.value = response
                        _barcodeState.emit(BarcodeState.SUCCESS)
                    }
                }.onFailure { throwable ->
                    _barcodeState.emit(BarcodeState.ERROR)
                    Timber.e("$throwable")
                }
        }
    }

    fun updateServerState() {
        _barcodeState.value = BarcodeState.IDLE
    }
}

sealed class BarcodeState {
    object IDLE : BarcodeState()
    object SUCCESS : BarcodeState()
    object ERROR : BarcodeState()
}
