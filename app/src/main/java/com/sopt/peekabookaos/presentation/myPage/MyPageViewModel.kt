package com.sopt.peekabookaos.presentation.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.usecase.ClearLocalPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val clearLocalPrefUseCase: ClearLocalPrefUseCase
) : ViewModel() {
    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> = _userData

    fun clearLocalPref() {
        clearLocalPrefUseCase()
    }
}
