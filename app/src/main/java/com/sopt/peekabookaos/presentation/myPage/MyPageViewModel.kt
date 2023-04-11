package com.sopt.peekabookaos.presentation.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.SelfIntro
import com.sopt.peekabookaos.domain.usecase.ClearLocalPrefUseCase
import com.sopt.peekabookaos.domain.usecase.GetMyPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyPageUseCase: GetMyPageUseCase,
    private val clearLocalPrefUseCase: ClearLocalPrefUseCase
) : ViewModel() {
    private val _userData = MutableLiveData<SelfIntro>()
    val userData: LiveData<SelfIntro> = _userData

    init {
        getMyPage()
    }

    private fun getMyPage() {
        viewModelScope.launch {
            getMyPageUseCase()
                .onSuccess { response ->
                    _userData.value = response
                    Timber.d("get 서버통신 성공")
                }
                .onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    fun clearLocalPref() {
        clearLocalPrefUseCase()
    }
}
