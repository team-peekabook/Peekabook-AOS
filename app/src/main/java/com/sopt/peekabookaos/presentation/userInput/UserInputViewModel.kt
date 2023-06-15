package com.sopt.peekabookaos.presentation.userInput

import android.app.Application
import android.net.Uri
import android.text.InputFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.usecase.PatchSignUpUseCase
import com.sopt.peekabookaos.domain.usecase.PostDuplicateUseCase
import com.sopt.peekabookaos.util.ContentUriRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val application: Application,
    private val postDuplicateUseCase: PostDuplicateUseCase,
    private val patchSignUpUseCase: PatchSignUpUseCase
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
    val profileImage: MutableLiveData<String> = _profileImage

    private val _isSignUpStatus = MutableLiveData<Boolean>()
    val isSignUpStatus: LiveData<Boolean> = _isSignUpStatus

    val nickname = MutableLiveData<String>()

    val introduce = MutableLiveData<String>()

    private val _isExclamationMarkEntered = MutableLiveData<Boolean>()
    val isExclamationMarkEntered: LiveData<Boolean> = _isExclamationMarkEntered

    private lateinit var profileImageUri: Uri

    private var filterAlphaNumSpace = InputFilter { source, _, _, _, _, _ ->
        val regularPattern = Pattern.compile(PATTERN)
        if (!regularPattern.matcher(source).matches()) {
            _isExclamationMarkEntered.value = true
            ""
        } else {
            _isExclamationMarkEntered.value = false
            source
        }
    }

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
    }

    fun patchSignUp() {
        val imageMultipartBody =
            if (profileImage.value != null) {
                if (::profileImageUri.isInitialized) {
                    ContentUriRequestBody(
                        application.baseContext,
                        "file",
                        profileImageUri
                    ).compressBitmap()
                } else {
                    null
                }
            } else null

        viewModelScope.launch {
            patchSignUpUseCase(
                file = imageMultipartBody,
                requestBodyMap = hashMapOf(
                    "nickname" to nickname.value!!.toRequestBody(),
                    "intro" to introduce.value!!.toRequestBody()
                )
            ).onSuccess { response ->
                _isSignUpStatus.value = response
            }.onFailure { throwable ->
                _isSignUpStatus.value = false
                Timber.e("$throwable")
            }
        }
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

    fun updateEditTextFilter(): Array<InputFilter> {
        return arrayOf(filterAlphaNumSpace)
    }

    fun updateCheckButtonState() {
        _isCheckButton.value = !(introduce.value.isNullOrBlank() || nickname.value.isNullOrBlank())
    }

    private fun String.toRequestBody(): RequestBody {
        return this.toRequestBody("application/json".toMediaTypeOrNull())
    }

    companion object {
        private const val PATTERN = "^[ㄱ-ㅣ가-힣a-zA-Z0-9]+$"
    }
}
