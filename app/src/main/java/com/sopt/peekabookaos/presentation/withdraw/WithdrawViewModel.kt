package com.sopt.peekabookaos.presentation.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.usecase.ClearLocalPrefUseCase
import com.sopt.peekabookaos.domain.usecase.DeleteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val clearLocalPrefUseCase: ClearLocalPrefUseCase
) : ViewModel() {
    private val _isWithdraw = MutableSharedFlow<Boolean>()
    val isWithdraw = _isWithdraw.asSharedFlow()

    fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase()
                .onSuccess { success ->
                    _isWithdraw.emit(success)
                    clearLocalPrefUseCase()
                    // TODO by 이빵주 : 온보딩 상태 초기화
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}
