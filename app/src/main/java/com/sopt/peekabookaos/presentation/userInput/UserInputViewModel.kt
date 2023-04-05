package com.sopt.peekabookaos.presentation.userInput

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.usecase.PostDuplicateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val postDuplicateUseCase: PostDuplicateUseCase
) : ViewModel() {
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

    fun getNickNameState() {
        viewModelScope.launch {
            postDuplicateUseCase(requireNotNull(nickname.value)).onSuccess { check ->
                _isNickname.value = (check == 1)
                updateNicknameMessage(true)
                updateDuplicateButtonState(requireNotNull(_isNickname.value))
            }.onFailure { throwable ->
                Timber.e("$throwable")
            }
        }
//        _isNickname.value = false
//        updateNicknameMessage(true)
//        updateDuplicateButtonState(requireNotNull(_isNickname.value))
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
