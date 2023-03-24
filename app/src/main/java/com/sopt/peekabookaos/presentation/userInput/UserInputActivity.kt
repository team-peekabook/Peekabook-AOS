package com.sopt.peekabookaos.presentation.userInput

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityUserInputBinding
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import kotlin.system.exitProcess

class UserInputActivity : BindingActivity<ActivityUserInputBinding>(R.layout.activity_user_input) {
    private val viewModel: UserInputViewModel by viewModels()
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.updateProfileImage(uri)
        }
    }
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initCheckClickListener()
        initDuplicateClickListener()
        initObserver()
        initProfileClickListener()
        initBackPressedCallback()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                onBackPressedTime = System.currentTimeMillis()
                ToastMessageUtil.showToast(
                    this@UserInputActivity,
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
        binding.tvUserInputCheck.setSingleOnClickListener {
            if (viewModel.isNickname.value == false) {
                val toMainActivity = Intent(this, MainActivity::class.java)
                startActivity(toMainActivity)
                finish()
            } else {
                viewModel.updateCheckMessage(viewModel.isNickname.value!!)
            }
        }
    }

    private fun initDuplicateClickListener() {
        binding.tvUserInputDuplicationCheck.setSingleOnClickListener {
            viewModel.getNickNameState()
        }
    }

    private fun initObserver() {
        viewModel.nickname.observe(this) {
            viewModel.updateCheckButtonState()
            viewModel.updateWritingState()
        }
        viewModel.introduce.observe(this) {
            viewModel.updateCheckButtonState()
        }
    }

    private fun initProfileClickListener() {
        binding.ivUserInputAdd.setSingleOnClickListener {
            launcher.launch("image/*")
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}
