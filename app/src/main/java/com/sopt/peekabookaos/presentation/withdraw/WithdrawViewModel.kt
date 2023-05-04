package com.sopt.peekabookaos.presentation.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.usecase.ClearLocalPrefUseCase
import com.sopt.peekabookaos.domain.usecase.DeleteUserUseCase
import com.sopt.peekabookaos.domain.usecase.SetSplashStateUseCase
import com.sopt.peekabookaos.util.UiEvent
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
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun deleteUser() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.IDLE)
            deleteUserUseCase()
                .onSuccess {
                    _uiEvent.emit(UiEvent.SUCCESS)
                    clearLocalPrefUseCase()
                    setSplashStateUseCase(SplashState.ONBOARDING)
                }.onFailure { throwable ->
                    _uiEvent.emit(UiEvent.ERROR)
                    Timber.e("$throwable")
                }
        }
    }
}
