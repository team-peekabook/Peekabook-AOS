package com.sopt.peekabookaos.presentation.registerBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.BookRegister

class RegisterBookViewModel : ViewModel() {
    private val _registerBookData = MutableLiveData<BookRegister>()
    val registerBookData: LiveData<BookRegister> = _registerBookData

    val comment = MutableLiveData("")

    val memo = MutableLiveData("")

    init {
        initRegisterBookData()
    }

    /* 더미데이터. 추후 서버 통신 시 제거 예정 */
    private fun initRegisterBookData() {
        _registerBookData.value = BookRegister(
            img = "http://image.yes24.com/goods/90365124/XL",
            title = "아무튼, 여름",
            author = "김신회",
            comment = "",
            memo = ""
        )
    }
}
