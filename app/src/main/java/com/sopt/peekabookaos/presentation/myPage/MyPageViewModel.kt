package com.sopt.peekabookaos.presentation.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.entity.SelfIntro

class MyPageViewModel : ViewModel() {
    private val _userData = MutableLiveData<SelfIntro>()
    val userData: LiveData<SelfIntro> = _userData
}
