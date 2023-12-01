package com.sopt.peekabookaos.presentation.main

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.usecase.SetAccessToMainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    setAccessToMainUseCase: SetAccessToMainUseCase
) : ViewModel() {
    init {
        setAccessToMainUseCase()
    }
}
