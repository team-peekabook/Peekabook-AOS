package com.sopt.peekabookaos.presentation.bookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.bookshelf.FriendProfileData
import com.sopt.peekabookaos.data.entity.bookshelf.UserData

class BookShelfViewModel : ViewModel() {
    private val _friendData: MutableLiveData<FriendProfileData> = MutableLiveData()
    val friendData: LiveData<FriendProfileData> = _friendData

    private val _pickData: MutableLiveData<FriendProfileData> = MutableLiveData()
    val pickData: LiveData<FriendProfileData> = _pickData

    private val _shelfData: MutableLiveData<FriendProfileData> = MutableLiveData()
    val shelfData: LiveData<FriendProfileData> = _shelfData

    private val _userData: MutableLiveData<UserData> = MutableLiveData()
    val userData: LiveData<UserData> = _userData

    init {
        initUserData()
    }

    private fun initUserData() {
        _userData.value = UserData(
            profile = "https://play-lh.googleusercontent.com/R8-LD7m5rwQwIdAit3PwUG8QgYoDecAZBSaEuPAjhTpsG6mkqo130b-RKm9RrXBj-kI",
            name = "문수빈",
            comment = "안녕하세요, 저는 한줄소개입니다! 제 책장을 둘러보세요."
        )
    }
}
