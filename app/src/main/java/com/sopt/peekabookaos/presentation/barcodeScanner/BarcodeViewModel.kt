package com.sopt.peekabookaos.presentation.barcodeScanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.repository.NaverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    private val naverRepository: NaverRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Book>())
    val uiState = _uiState.asStateFlow()

    private val _serverStatus = MutableStateFlow<BarcodeState>(BarcodeState.IDLE)
    val serverStatus = _serverStatus.asStateFlow()

    fun postBarcode(barcodeString: String) {
        viewModelScope.launch {
            _serverStatus.emit(BarcodeState.IDLE)
            naverRepository.getBookToBarcode(barcodeString)
                .onSuccess { response ->
                    if (response.isEmpty()) {
                        _serverStatus.emit(BarcodeState.ERROR)
                    } else {
                        _uiState.value = response
                        _serverStatus.emit(BarcodeState.SUCCESS)
                        Thread.sleep(1)
                    }
                }.onFailure { throwable ->
                    _serverStatus.emit(BarcodeState.ERROR)
                    Timber.e("$throwable")
                }
        }
    }

    fun initServerStatus() {
        _serverStatus.value = BarcodeState.IDLE
    }
}

sealed class BarcodeState {
    object IDLE : BarcodeState()
    object SUCCESS : BarcodeState()
    object ERROR : BarcodeState()
}
