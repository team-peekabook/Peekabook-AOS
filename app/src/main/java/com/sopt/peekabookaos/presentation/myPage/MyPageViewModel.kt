package com.sopt.peekabookaos.presentation.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.SelfIntro
import com.sopt.peekabookaos.domain.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : ViewModel() {
    private val _userData = MutableLiveData<List<SelfIntro>>()
    val userData: LiveData<List<SelfIntro>> = _userData

    private val _isServerStatus = MutableLiveData(false)
    val isServerStatus: LiveData<Boolean> = _isServerStatus

    init {
        getMyPage()
    }

    private fun getMyPage() {
        viewModelScope.launch {
            myPageRepository.getMyPage()
                .onSuccess { response ->
                    _userData.value = response
                    _isServerStatus.value = true
                }
                .onFailure { throwable ->
                    _isServerStatus.value = false
                    Timber.e("$throwable")
                }
        }
    }
}
