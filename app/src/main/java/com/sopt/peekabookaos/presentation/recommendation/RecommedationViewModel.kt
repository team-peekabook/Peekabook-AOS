package com.sopt.peekabookaos.presentation.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.FriendUser

class RecommedationViewModel : ViewModel() {
    private val _bookData = MutableLiveData<Book>()
    val bookData: LiveData<Book> = _bookData

    // friendData 만들고
    private val _friendData = MutableLiveData<FriendUser>()
    val friendData: LiveData<FriendUser> = _friendData

    val comment = MutableLiveData("")

    fun initRecommendData(bookData: Book, friendData: FriendUser) { // 파라미터로 친구데이터도 받아서
        _bookData.value = bookData
        _friendData.value = friendData
        // 넘어오는 intent값이 없어서 더미값 넣음. 추후 ~Dummy->~Data로 변경 예정
    }
}
