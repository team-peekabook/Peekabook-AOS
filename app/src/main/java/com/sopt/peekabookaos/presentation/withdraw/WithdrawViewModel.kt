package com.sopt.peekabookaos.presentation.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.usecase.ClearLocalPrefUseCase
import com.sopt.peekabookaos.domain.usecase.DeleteUserUseCase
import com.sopt.peekabookaos.domain.usecase.SetSplashStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val clearLocalPrefUseCase: ClearLocalPrefUseCase,
    private val setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    private val _isWithdraw = MutableSharedFlow<Boolean>()
    val isWithdraw = _isWithdraw.asSharedFlow()

    fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase()
                .onSuccess { success ->
                    _isWithdraw.emit(success)
                    clearLocalPrefUseCase()
                    setSplashStateUseCase(SplashState.ONBOARDING)
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}
