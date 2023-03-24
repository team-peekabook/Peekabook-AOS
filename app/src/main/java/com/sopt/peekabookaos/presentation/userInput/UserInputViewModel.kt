package com.sopt.peekabookaos.presentation.userInput

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInputViewModel : ViewModel() {
    private val _isNickname: MutableLiveData<Boolean> = MutableLiveData(true)
    val isNickname: LiveData<Boolean> = _isNickname

    private val _isNicknameMessage: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNicknameMessage: LiveData<Boolean> = _isNicknameMessage

    private val _isCheckMessage: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCheckMessage: LiveData<Boolean> = _isCheckMessage

    private val _isDuplicateButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDuplicateButton: LiveData<Boolean> = _isDuplicateButton

    private val _isCheckButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCheckButton: LiveData<Boolean> = _isCheckButton

    private val _profileImage: MutableLiveData<String> = MutableLiveData()
    val profileImage: LiveData<String> = _profileImage

    val nickname = MutableLiveData<String>()

    val introduce = MutableLiveData<String>()

    var nicknameList = listOf("문수빈", "한새연", "텽", "a", "bc")

    fun getNickNameState() {
        _isNickname.value = nicknameList.contains(nickname.value)
        updateNicknameMessage(true)
        updateDuplicateButtonState(_isNickname.value!!)
    }

    fun updateWritingState() {
        _isNickname.value = true
        updateNicknameMessage(false)
        updateCheckMessage(false)
        updateDuplicateButtonState(!nickname.value.isNullOrBlank())
    }

    fun updateCheckMessage(state: Boolean) {
        _isCheckMessage.value = state
    }

    private fun updateNicknameMessage(state: Boolean) {
        _isNicknameMessage.value = state
    }

    private fun updateDuplicateButtonState(state: Boolean) {
        _isDuplicateButton.value = state
    }

    fun updateProfileImage(uri: Uri) {
        _profileImage.value = uri.toString()
    }

    fun updateCheckButtonState() {
        _isCheckButton.value = !(introduce.value.isNullOrBlank() || nickname.value.isNullOrBlank())
    }
}
