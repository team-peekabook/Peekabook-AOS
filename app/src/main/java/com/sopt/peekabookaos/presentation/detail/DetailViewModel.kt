package com.sopt.peekabookaos.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Book

class DetailViewModel : ViewModel() {
    private val _BookData = MutableLiveData<Book>()
    val BookData: LiveData<Book> = _BookData

    init {
        initDetailData()
    }

    private fun initDetailData() {
        _BookData.value = Book(
            bookImage = "http://image.yes24.com/goods/90365124/XL",
            bookTitle = "아무튼, 여름",
            author = "김신회",
            description = "이 책 너무 인상깊어요",
            memo = "입학 선물로 받은 책"
        )
    }
}
