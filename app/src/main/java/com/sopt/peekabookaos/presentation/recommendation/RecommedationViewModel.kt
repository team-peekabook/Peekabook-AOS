package com.sopt.peekabookaos.presentation.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.FriendUser

class RecommedationViewModel : ViewModel() {
    private val _bookData = MutableLiveData<Book>()
    val bookData: LiveData<Book> = _bookData

    private val _friendData = MutableLiveData<FriendUser>()
    val friendData: LiveData<FriendUser> = _friendData

    val comment = MutableLiveData("")

    fun initRecommendData(bookData: Book, friendData: FriendUser) {
        _bookData.value = bookData
        _friendData.value = friendData
    }

    fun post() {
        /** 서버통신 함수 여기에 만들면 되고 함수명은 수정하세요 ~ */
    }
}
