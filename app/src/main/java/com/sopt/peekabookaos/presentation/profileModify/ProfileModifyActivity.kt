package com.sopt.peekabookaos.presentation.profileModify

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
                1 -> {
                    profileModifyViewModel.removeProfileImage()
                }
            }
        }
        profileModifyBottomSheetFragment.show(
            supportFragmentManager,
            profileModifyBottomSheetFragment.tag
        )
    }

    @Override
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ToastMessageUtil.showToast(this, getString(R.string.profile_modify_toast_reject))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CREATE_EX) {
            photoURI?.let { profileModifyViewModel.updateProfileImage(it) }
        }
    }

    private fun initCheckClickListener() {
        binding.btnProfileModifyCheck.setSingleOnClickListener {
            KeyBoardUtil.hide(this)
            val isNicknameInUse = profileModifyViewModel.isNicknameInUse.value
            val currentNickname = profileModifyViewModel.nickname.value
            val initialNickname = profileModifyViewModel.getInitialNickname()

            if (isNicknameInUse == false || currentNickname == initialNickname) {
                profileModifyViewModel.patchProfileModify()
            } else {
                profileModifyViewModel.updateCheckMessage(requireNotNull(isNicknameInUse))
            }
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
            profileModifyViewModel.removeCheckMessage()
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
        val maxLength = 6
        val filters =
            arrayOf(InputFilter.LengthFilter(maxLength), profileModifyViewModel.filterAlphaNumSpace)
        binding.etProfileModifyNickname.filters = filters
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CREATE_EX = 3
    }
}
