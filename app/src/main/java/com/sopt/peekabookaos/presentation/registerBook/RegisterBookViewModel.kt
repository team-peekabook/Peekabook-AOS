package com.sopt.peekabookaos.presentation.registerBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.BookRegister
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RegisterBookViewModel : ViewModel() {
    private val _registerBookData = MutableLiveData<BookRegister>()
    val registerBookData: LiveData<BookRegister> = _registerBookData
}
