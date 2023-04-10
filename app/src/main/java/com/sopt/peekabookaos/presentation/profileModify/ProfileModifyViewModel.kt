package com.sopt.peekabookaos.presentation.profileModify

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileModifyViewModel : ViewModel() {
    private val _isNickname: MutableLiveData<Boolean> = MutableLiveData(true)
    val isNickname: LiveData<Boolean> = _isNickname

    private val _isNicknameMessage: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNicknameMessage: LiveData<Boolean> = _isNicknameMessage

    private val _isSignUpStatus = MutableLiveData<Boolean>()
    val isSignUpStatus: LiveData<Boolean> = _isSignUpStatus

    private val _isCheckMessage: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _isDuplicateButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDuplicateButton: LiveData<Boolean> = _isDuplicateButton

    private val _isCheckButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCheckButton: LiveData<Boolean> = _isCheckButton

    private val _profileImage: MutableLiveData<String> = MutableLiveData()
    val profileImage: LiveData<String> = _profileImage

    val nickname = MutableLiveData<String>()

    val modify = MutableLiveData<String>()

    val introduce = MutableLiveData<String>()

    private var nicknameList = listOf("a", "박강희", "이영주", "김하정", "피카북")

    private lateinit var profileImageUri: Uri

    fun getNickNameState() {
        _isNickname.value = nicknameList.contains(nickname.value)
        updateNicknameMessage(true)
        updateDuplicateButtonState(requireNotNull(_isNickname.value))
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
        profileImageUri = uri
        _profileImage.value = uri.toString()
    }

    fun updateCheckButtonState() {
        _isCheckButton.value = !(modify.value.isNullOrBlank() || nickname.value.isNullOrBlank())
    }
}
