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

    private val _isDuplicate: MutableLiveData<Boolean> = MutableLiveData()
    val isDuplicate: LiveData<Boolean> = _isDuplicate

    private val _isCheck: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCheck: LiveData<Boolean> = _isCheck

    var nicknameList = listOf("문수빈", "한새연", "텽", "a")

    fun getDuplication() {
        _isNicknameDuplicate.value = nicknameList.contains(nickname.value)
    }

    fun updateNicknameCheck() {
        _isNicknameCheck.value = _isNicknameDuplicate.value
    }

    fun updateProfileImage(uri: Uri) {
        _profileImage.value = uri.toString()
    }

    fun updateButtonState() {
        _isCheck.value = !(introduce.value.isNullOrBlank() || nickname.value.isNullOrBlank())
        _isDuplicate.value = !nickname.value.isNullOrBlank()
    }
}
