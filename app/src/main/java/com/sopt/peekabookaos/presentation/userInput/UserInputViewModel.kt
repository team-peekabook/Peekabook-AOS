package com.sopt.peekabookaos.presentation.userInput

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInputViewModel : ViewModel() {
    private val _isNicknameDuplicate: MutableLiveData<Boolean> = MutableLiveData()
    val isNicknameDuplicate: LiveData<Boolean> = _isNicknameDuplicate

    private val _isNicknameCheck: MutableLiveData<Boolean> = MutableLiveData()
    val isNicknameCheck: LiveData<Boolean> = _isNicknameCheck

    private val _profileImage: MutableLiveData<String> = MutableLiveData()
    val profileImage: LiveData<String> = _profileImage

    val nickname = MutableLiveData<String>()

    val introduce = MutableLiveData<String>()

    var nicknameList = listOf("문수빈", "한새연", "텽", "a")

    fun getDuplication() {
        _isNicknameDuplicate.value = nicknameList.contains(nickname.value)
    }

    fun updateNicknameCheck() {
        _isNicknameCheck.value = _isNicknameDuplicate.value
    }

    fun updateInputState(): Boolean {
        return introduce.value.isNullOrBlank() || nickname.value.isNullOrBlank()
    }

    fun updateProfileImage(uri: Uri) {
        _profileImage.value = uri.toString()
    }
}
