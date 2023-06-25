package com.sopt.peekabookaos.presentation.profileModify

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.InputFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.usecase.PatchProfileModifyUseCase
import com.sopt.peekabookaos.domain.usecase.PostDuplicateUseCase
import com.sopt.peekabookaos.util.ContentUriRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ProfileModifyViewModel @Inject constructor(
    private val application: Application,
    private val postDuplicateUseCase: PostDuplicateUseCase,
    private val patchProfileModifyUseCase: PatchProfileModifyUseCase
) : ViewModel() {
    private val _isNickname: MutableLiveData<Boolean> = MutableLiveData(true)
    val isNickname: LiveData<Boolean> = _isNickname

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

    private var filterAlphaNumSpace = InputFilter { source, _, _, _, _, _ ->
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
            postDuplicateUseCase(requireNotNull(nickname.value)).onSuccess { check ->
                _isNickname.value = (check == 1)
                updateNicknameMessage(true)
                updateDuplicateButtonState(requireNotNull(_isNickname.value))
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
                    val bitmap = urlToBitmap(profileImage.value!!)
                    val uri = getImageUri(application.baseContext, bitmap!!)
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

    private suspend fun urlToBitmap(url: String): Bitmap? = withContext(Dispatchers.IO) {
        return@withContext try {
            val connection = URL(url).openConnection()
            connection.doInput = true
            connection.connect()
            val input = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    fun updateEditTextFilter(): Array<InputFilter> {
        return arrayOf(filterAlphaNumSpace)
    }

    fun updateWritingState() {
        _isNickname.value = true
        updateNicknameMessage(false)
        updateCheckMessage(false)
        updateDuplicateButtonState(!nickname.value.isNullOrBlank())
    }

    private fun updateCheckMessage(state: Boolean) {
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
        _isCheckButton.value = !(introduce.value.isNullOrBlank() || nickname.value.isNullOrBlank())
    }

    fun setPreviousInfo(userData: User) {
        _profileImage.value = userData.profileImage
        nickname.value = userData.nickname
        introduce.value = userData.intro
    }

    private fun String.toRequestBody(): RequestBody {
        return this.toRequestBody("application/json".toMediaTypeOrNull())
    }

    companion object {
        private const val PATTERN = "^[ㄱ-ㅣ가-힣a-zA-Z0-9\\u318D\\u119E]+$"
    }
}
