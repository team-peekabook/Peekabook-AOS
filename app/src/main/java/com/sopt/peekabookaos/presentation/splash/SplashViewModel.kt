package com.sopt.peekabookaos.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.BuildConfig
import com.sopt.peekabookaos.domain.entity.ForcedUpdate
import com.sopt.peekabookaos.domain.entity.SplashUiState
import com.sopt.peekabookaos.domain.usecase.GetSignedUpUseCase
import com.sopt.peekabookaos.domain.usecase.HasUpdateVersionCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val hasUpdateVersionCheckUseCase: HasUpdateVersionCheckUseCase,
    private val getSignedUpUseCase: GetSignedUpUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Idle)
    val uiState = _uiState.asSharedFlow()

    init {
        checkHasUpdate()
    }

    private fun isSignedUp(): Boolean = getSignedUpUseCase()

    private fun checkHasUpdate() = viewModelScope.launch {
        _uiState.emit(SplashUiState.Idle)
        hasUpdateVersionCheckUseCase(BuildConfig.VERSION_NAME)
            .onSuccess { update ->
                when (update) {
                    is ForcedUpdate.None -> {
                        if (isSignedUp()) {
                            _uiState.emit(SplashUiState.CanStartMain)
                        } else {
                            _uiState.emit(SplashUiState.CanStartOnboarding)
                        }
                    }

                    is ForcedUpdate.Need -> {
                        _uiState.emit(SplashUiState.ForceUpdate(update.updateInformation))
                    }
                }
            }.onFailure { throwable ->
                _uiState.emit(SplashUiState.Error)
                Timber.e("$throwable")
            }
    }
}
