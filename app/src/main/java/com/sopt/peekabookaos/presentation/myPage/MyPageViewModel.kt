package com.sopt.peekabookaos.presentation.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.SelfIntro
import com.sopt.peekabookaos.domain.usecase.ClearLocalPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val clearLocalPrefUseCase: ClearLocalPrefUseCase
) : ViewModel() {
    private val _userData = MutableLiveData<SelfIntro>()
    val userData: LiveData<SelfIntro> = _userData

    fun clearLocalPref() {
        viewModelScope.launch {
            clearLocalPrefUseCase()
        }
    }
}
