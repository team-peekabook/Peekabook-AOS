package com.sopt.peekabookaos.presentation.profileModify

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.usecase.PatchProfileModifyUseCase
import com.sopt.peekabookaos.domain.usecase.PostDuplicateUseCase
import com.sopt.peekabookaos.util.ContentUriRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
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

    private val _isDuplicateButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDuplicateButton: LiveData<Boolean> = _isDuplicateButton

    private val _isCheckButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCheckButton: LiveData<Boolean> = _isCheckButton

    private val _profileImage: MutableLiveData<String> = MutableLiveData()
    val profileImage: LiveData<String> = _profileImage

    val nickname = MutableLiveData("")

    val introduce = MutableLiveData("")

    private lateinit var profileImageUri: Uri

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
        val imageMultipartBody =
            if (::profileImageUri.isInitialized) {
                ContentUriRequestBody(
                    application.baseContext,
                    "file",
                    profileImageUri
                ).compressBitmap()
            } else {
                basicProfileToMultiPart()
            }

        viewModelScope.launch {
            patchProfileModifyUseCase(
                file = imageMultipartBody,
                requestBodyMap = hashMapOf(
                    "nickname" to nickname.value!!.toRequestBody(),
                    "intro" to introduce.value!!.toRequestBody()
                )
            ).onSuccess { response ->
                _isModifyStatus.value = response
            }.onFailure { throwable ->
                _isModifyStatus.value = false
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

    private fun basicProfileToMultiPart(): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val resId = R.drawable.ic_user_input_profile
        val drawable = ContextCompat.getDrawable(application.baseContext, resId)
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable?.intrinsicWidth ?: 0,
            drawable?.intrinsicHeight ?: 0,
            Bitmap.Config.ARGB_8888
        )
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val canvas = Canvas(bitmap)
        requireNotNull(drawable).setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmapToMultipart(bitmap, "file", "basic_profile.jpg")
    }

    private fun bitmapToMultipart(
        bitmap: Bitmap,
        paramName: String,
        fileName: String
    ): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)

        val requestBody = RequestBody.Companion.create(
            "multipart/form-data".toMediaTypeOrNull(),
            byteArrayOutputStream.toByteArray()
        )
        return MultipartBody.Part.createFormData(paramName, fileName, requestBody)
    }
}
