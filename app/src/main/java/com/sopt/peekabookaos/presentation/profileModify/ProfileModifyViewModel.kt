package com.sopt.peekabookaos.presentation.profileModify

import android.app.Application
import android.net.Uri
import android.text.InputFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.usecase.PatchProfileModifyUseCase
import com.sopt.peekabookaos.domain.usecase.PostNicknameDuplicateUseCase
import com.sopt.peekabookaos.util.ContentUriRequestBody
import com.sopt.peekabookaos.util.ImageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ProfileModifyViewModel @Inject constructor(
    private val application: Application,
    private val postNicknameDuplicateUseCase: PostNicknameDuplicateUseCase,
    private val patchProfileModifyUseCase: PatchProfileModifyUseCase
) : ViewModel() {
    private val _isNicknameInUse: MutableLiveData<Boolean> = MutableLiveData(true)
    val isNicknameInUse: LiveData<Boolean> = _isNicknameInUse

    private val _isNicknameMessage: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNicknameMessage: LiveData<Boolean> = _isNicknameMessage

    private val _isModifyStatus = MutableLiveData<Boolean>()
    val isModifyStatus: LiveData<Boolean> = _isModifyStatus

    private val _isCheckMessage: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCheckMessage: LiveData<Boolean> = _isCheckMessage

    private val _isDuplicateButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDuplicateButton: LiveData<Boolean> = _isDuplicateButton

    private val _isCheckButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCheckButton: LiveData<Boolean> = _isCheckButton

    private val _profileImage: MutableLiveData<String?> = MutableLiveData()
    val profileImage: LiveData<String?> = _profileImage

    val nickname = MutableLiveData("")

    val introduce = MutableLiveData("")

    private val _isExclamationMarkEntered = MutableLiveData<Boolean>()
    val isExclamationMarkEntered: LiveData<Boolean> = _isExclamationMarkEntered

    private lateinit var profileImageUri: Uri

    private var previousNickname: String = ""

    var filterAlphaNumSpace = InputFilter { source, _, _, _, _, _ ->
        val regularPattern = Pattern.compile(PATTERN)
        if (source.isNullOrBlank() || regularPattern.matcher(source).matches()) {
            _isExclamationMarkEntered.value = false
            source
        } else {
            _isExclamationMarkEntered.value = true
            ""
        }
    }

    fun getNickNameState() {
        viewModelScope.launch {
            postNicknameDuplicateUseCase(requireNotNull(nickname.value))
                .onSuccess { check ->
                    _isNicknameInUse.value = (check == 1)
                    updateNicknameMessage(true)
                    updateDuplicateButtonState(requireNotNull(_isNicknameInUse.value))
                    updateCheckButtonState()
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    fun patchProfileModify() {
        viewModelScope.launch {
            val imageMultipartBody = if (profileImage.value != null) {
                if (::profileImageUri.isInitialized) {
                    ContentUriRequestBody(
                        application.baseContext,
                        "file",
                        profileImageUri
                    ).compressBitmap()
                } else {
                    val bitmap = ImageUtil.urlToBitmap(requireNotNull(profileImage.value))
                    val uri = ImageUtil.getImageUri(application.baseContext, requireNotNull(bitmap))
                    ContentUriRequestBody(
                        application.baseContext,
                        "file",
                        uri
                    ).compressBitmap()
                }
            } else null

            patchProfileModifyUseCase(
                file = imageMultipartBody,
                requestBodyMap = hashMapOf(
                    "nickname" to requireNotNull(nickname.value).toRequestBody(),
                    "intro" to requireNotNull(introduce.value).toRequestBody()
                )
            ).onSuccess { response ->
                _isModifyStatus.value = response
            }.onFailure { throwable ->
                Timber.e("$throwable")
            }
        }
    }

    fun updateWritingState() {
        _isNicknameInUse.value = true
        updateNicknameMessage(false)
        updateCheckMessage(false)
        val previousNickname = getPreviousNickname()
        if (nickname.value == previousNickname) {
            updateDuplicateButtonState(false)
        } else {
            updateDuplicateButtonState(!nickname.value.isNullOrBlank())
        }
    }

    private fun getPreviousNickname(): String {
        return previousNickname
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

    fun removeCheckMessage() {
        if (_isNicknameMessage.value == false) {
            _isCheckMessage.value = false
            updateCheckMessage(false)
        }
    }

    fun updateProfileImage(uri: Uri) {
        profileImageUri = uri
        _profileImage.value = uri.toString()
    }

    fun updateCheckButtonState() {
        _isCheckButton.value = !(introduce.value.isNullOrBlank() || nickname.value.isNullOrBlank())
    }

    fun setPreviousInfo(userData: User) {
        previousNickname = userData.nickname
        _profileImage.value = userData.profileImage
        nickname.value = userData.nickname
        introduce.value = userData.intro
    }

    fun getInitialNickname(): String {
        return previousNickname
    }

    fun removeProfileImage() {
        _profileImage.value = null
    }

    private fun String.toRequestBody(): RequestBody {
        return this.toRequestBody("application/json".toMediaTypeOrNull())
    }

    companion object {
        private const val PATTERN = "^[ㄱ-ㅣ가-힣a-zA-Z0-9\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$"
    }
}
