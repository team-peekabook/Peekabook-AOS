package com.sopt.peekabookaos.presentation.profileModify

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityProfileModifyBinding
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.presentation.myPage.MyPageFragment.Companion.USER_INFO
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.getParcelable
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class ProfileModifyActivity :
    BindingActivity<ActivityProfileModifyBinding>(R.layout.activity_profile_modify) {
    private val profileModifyViewModel: ProfileModifyViewModel by viewModels()
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            profileModifyViewModel.updateProfileImage(uri)
        }
    }
    private var photoURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = profileModifyViewModel
        setPreviousInfo()
        initBackClickListener()
        initEditTextClearFocus()
        initCheckClickListener()
        initDuplicateClickListener()
        initObserver()
        initAddClickListener()
    }

    private fun initBackClickListener() {
        binding.btnProfileModifyBack.setSingleOnClickListener {
            finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clProfileModify.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(this)
            return@setOnTouchListener false
        }
    }

    private fun initAddClickListener() {
        binding.btnProfileModifyAdd.setSingleOnClickListener { profileBottomSheet() }
        binding.ivProfileModifyImage.setSingleOnClickListener { profileBottomSheet() }
    }

    private fun profileBottomSheet() {
        val profileModifyBottomSheetFragment = ProfileModifyBottomSheetFragment.onItemClick {
            when (it) {
                0 -> launcher.launch("image/*")
                1 -> if (checkPermission()) {
                    dispatchTakePictureIntentEx()
                } else {
                    requestCameraPermission()
                }
                2 -> {
                    binding.ivProfileModifyImage.setImageResource(R.drawable.ic_user_input_profile)
                    profileModifyViewModel._profileImage.value = null
                }
                else -> {
                    requestCameraPermission()
                }
            }
        }
        profileModifyBottomSheetFragment.show(
            supportFragmentManager,
            profileModifyBottomSheetFragment.tag
        )
    }

    private fun requestCameraPermission() {
        requestPermissionLauncher.launch(
            arrayOf(
                CAMERA,
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE
            )
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions: Map<String, Boolean> ->
            val isCameraPermissionGranted =
                permissions[CAMERA] != null && requireNotNull(permissions[CAMERA])
            val isWriteStoragePermissionGranted =
                permissions[WRITE_EXTERNAL_STORAGE] != null && requireNotNull(permissions[WRITE_EXTERNAL_STORAGE])
            val isReadStoragePermissionGranted =
                permissions[READ_EXTERNAL_STORAGE] != null && requireNotNull(permissions[READ_EXTERNAL_STORAGE])
            if (isCameraPermissionGranted && isWriteStoragePermissionGranted && isReadStoragePermissionGranted) {
                dispatchTakePictureIntentEx()
            }
        }

    private fun checkPermission(): Boolean {
        return (
            ContextCompat.checkSelfPermission(this, CAMERA)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            )
    }

    @Override
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ToastMessageUtil.showToast(this, getString(R.string.profile_modify_toast_reject))
        }
    }

    private fun dispatchTakePictureIntentEx() {
        val timeStamp: String =
            SimpleDateFormat(getString(R.string.profile_modify_timeStamp)).format(Date())
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val uri: Uri? = createImageUri("JPEG_${timeStamp}_", "image/jpeg")
        photoURI = uri
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(takePictureIntent, REQUEST_CREATE_EX)
    }

    private fun createImageUri(filename: String, mimeType: String): Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CREATE_EX) {
            photoURI?.let { profileModifyViewModel.updateProfileImage(it) }
        }
    }

    private fun initCheckClickListener() {
        binding.btnProfileModifyCheck.setSingleOnClickListener {
            profileModifyViewModel.patchProfileModify()
        }
    }

    private fun setPreviousInfo() {
        profileModifyViewModel.setPreviousInfo(
            userData = intent.getParcelable(USER_INFO, User::class.java) ?: User()
        )
    }

    private fun initDuplicateClickListener() {
        binding.tvProfileModifyDuplicationCheck.setSingleOnClickListener {
            profileModifyViewModel.getNickNameState()
        }
    }

    private fun initObserver() {
        profileModifyViewModel.nickname.observe(this) {
            profileModifyViewModel.updateCheckButtonState()
            profileModifyViewModel.updateWritingState()
            checkRegularExpression()
        }
        profileModifyViewModel.introduce.observe(this) {
            profileModifyViewModel.updateCheckButtonState()
        }
        profileModifyViewModel.isModifyStatus.observe(this) { success ->
            if (success) {
                finish()
            }
        }
        profileModifyViewModel.isExclamationMarkEntered.observe(this) { exclamationMark ->
            if (exclamationMark) {
                ToastMessageUtil.showToast(
                    this,
                    this.resources.getString(R.string.user_input_regular_expression)
                )
            }
        }
    }

    private fun checkRegularExpression() {
        binding.etProfileModifyNickname.filters = profileModifyViewModel.updateEditTextFilter()
    }

    companion object {
        private const val REQUEST_CREATE_EX = 3
    }
}
