package com.sopt.peekabookaos.presentation.profileModify

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityProfileModifyBinding
import com.sopt.peekabookaos.presentation.myPage.MyPageFragment
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import kotlin.system.exitProcess

class ProfileModifyActivity :
    BindingActivity<ActivityProfileModifyBinding>(R.layout.activity_profile_modify) {
    private val viewModel: ProfileModifyViewModel by viewModels()
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.updateProfileImage(uri)
        }
    }
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initEditTextClearFocus()
        initCheckClickListener()
        initBackClickListener()
        initDuplicateClickListener()
        initObserver()
        initProfileClickListener()
        initBackPressedCallback()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clProfileModify.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(this)
            return@setOnTouchListener false
        }
    }

    private fun initBackClickListener() {
        binding.btnProfileModifyBack.setSingleOnClickListener {
            finish()
        }
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                onBackPressedTime = System.currentTimeMillis()
                ToastMessageUtil.showToast(
                    this@ProfileModifyActivity,
                    getString(R.string.finish_app_toast_msg)
                )
            } else {
                finishAffinity()
                System.runFinalization()
                exitProcess(0)
            }
        }
    }

    private fun initCheckClickListener() {
        binding.tvProfileModifyCheck.setSingleOnClickListener {
            if (viewModel.isNickname.value == false) {
                val toMyPageFragment = Intent(this, MyPageFragment::class.java)
                startActivity(toMyPageFragment)
                finish()
            } else {
                viewModel.updateCheckMessage(requireNotNull(viewModel.isNickname.value))
            }
        }
    }

    private fun initDuplicateClickListener() {
        binding.tvProfileModifyCheck.setSingleOnClickListener {
            viewModel.getNickNameState()
        }
    }

    private fun initObserver() {
        viewModel.nickname.observe(this) {
            viewModel.updateCheckButtonState()
            viewModel.updateWritingState()
        }
        viewModel.modify.observe(this) {
            viewModel.updateCheckButtonState()
        }
    }

    private fun initProfileClickListener() {
        binding.ivProfileModifyAdd.setSingleOnClickListener {
            launcher.launch("image/*")
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}

