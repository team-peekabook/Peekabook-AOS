package com.sopt.peekabookaos.presentation.userInput

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityUserInputBinding
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener

class UserInputActivity : BindingActivity<ActivityUserInputBinding>(R.layout.activity_user_input) {
    private val viewModel: UserInputViewModel by viewModels()
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.updateProfileImage(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        initCheckClickListener()
        initBackClickListener()
        initDuplicateClickListener()
        initObserver()
        initProfileClickListener()
    }

    private fun initBackClickListener() {
        binding.ivUserInputBack.setSingleOnClickListener {
            finish()
        }
    }

    private fun initCheckClickListener() {
        binding.btnUserInputCheck.setSingleOnClickListener {
            if (viewModel.isNicknameDuplicate.value == !DUPLICATION) {
                val toMainActivity = Intent(this, MainActivity::class.java)
                startActivity(toMainActivity)
            } else {
                viewModel.updateNicknameCheck()
            }
        }
    }

    private fun initDuplicateClickListener() {
        binding.btnUserInputDuplicationCheck.setSingleOnClickListener {
            viewModel.getDuplication()
        }
    }

    private fun initObserver() {
        viewModel.nickname.observe(this) {
            updateButtonState(binding.btnUserInputCheck, viewModel.updateInputState())
            updateButtonState(
                binding.btnUserInputDuplicationCheck,
                viewModel.nickname.value.isNullOrBlank()
            )
        }
        viewModel.introduce.observe(this) {
            updateButtonState(binding.btnUserInputCheck, viewModel.updateInputState())
        }
    }

    private fun updateButtonState(button: AppCompatButton, bool: Boolean) {
        if (bool) {
            with(button) {
                isEnabled = false
                background = getDrawable(R.drawable.shape_gray_fill_0_rect)
            }
        } else {
            with(button) {
                isEnabled = true
                background = getDrawable(R.drawable.shape_red_fill_0_rect)
            }
        }
    }

    private fun initProfileClickListener() {
        binding.ivUserInputAdd.setSingleOnClickListener {
            launcher.launch("image/*")
        }
    }

    companion object {
        private const val DUPLICATION = true
    }
}
