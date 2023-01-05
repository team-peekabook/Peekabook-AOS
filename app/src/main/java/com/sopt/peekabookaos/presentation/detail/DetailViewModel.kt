package com.sopt.peekabookaos.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Book

class DetailViewModel : ViewModel() {
    private val _BookData = MutableLiveData<Book>()
    val BookData: LiveData<Book> = _BookData

    init {
        nullDetailData()
    }

    private fun nullDetailData() {
        _BookData.value = Book(
            bookImage = "http://image.yes24.com/goods/90365124/XL",
            bookTitle = "아무튼, 여름",
            author = "김신회",
            description = null,
            memo = null
        )
    }
}
