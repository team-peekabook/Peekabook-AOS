package com.sopt.peekabookaos.presentation.userInput

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentUserInputBinding
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInputFragment : BindingFragment<FragmentUserInputBinding>(R.layout.fragment_user_input) {
    private val viewModel: UserInputViewModel by viewModels()
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.updateProfileImage(uri)
        }
    }
    private var photoURI: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initBackPressedCallback()
        initBackClickListener()
        initEditTextClearFocus()
        initCheckClickListener()
        initDuplicateClickListener()
        initObserver()
        initAddClickListener()
    }

    private fun initBackClickListener() {
        binding.btnUserInputBack.setSingleOnClickListener {
            findNavController().navigate(R.id.action_userInputFragment_to_socialLoginFragment)
        }
    }

    private fun initBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_userInputFragment_to_socialLoginFragment)
                }
            }
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clUserInput.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(requireActivity())
            return@setOnTouchListener false
        }
    }

    private fun initAddClickListener() {
        binding.btnUserInputAdd.setSingleOnClickListener { profileBottomSheet() }
        binding.ivUserInputProfile.setSingleOnClickListener { profileBottomSheet() }
    }

    private fun profileBottomSheet() {
        val userInputBottomSheetFragment = UserInputBottomSheetFragment.onItemClick {
            when (it) {
                0 -> launcher.launch("image/*")
                1 -> viewModel.removeProfileImage()
                else -> throw IndexOutOfBoundsException()
            }
        }
        userInputBottomSheetFragment.show(
            parentFragmentManager,
            userInputBottomSheetFragment.tag
        )
    }

    private fun checkRegularExpression() {
        val maxLength = 6
        val filters = arrayOf(InputFilter.LengthFilter(maxLength), viewModel.filterAlphaNumSpace)
        binding.etUserInputNickname.filters = filters
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CREATE_EX) {
            photoURI?.let {
                viewModel.updateProfileImage(it)
            }
        }
    }

    private fun initCheckClickListener() {
        binding.tvUserInputCheck.setSingleOnClickListener {
            if (viewModel.isNickname.value == false) {
                viewModel.patchSignUp()
            } else {
                viewModel.updateCheckMessage(requireNotNull(viewModel.isNickname.value))
            }
        }
    }

    private fun initDuplicateClickListener() {
        binding.tvUserInputDuplicationCheck.setSingleOnClickListener {
            viewModel.getNickNameState()
            viewModel.removeCheckMessage()
        }
    }

    private fun initObserver() {
        viewModel.nickname.observe(requireActivity()) {
            viewModel.updateCheckButtonState()
            viewModel.updateWritingState()
            checkRegularExpression()
        }
        viewModel.introduce.observe(requireActivity()) {
            viewModel.updateCheckButtonState()
        }
        viewModel.isSignUpStatus.observe(requireActivity()) { success ->
            if (success) {
                val toMainActivity = Intent(requireActivity(), MainActivity::class.java)
                startActivity(toMainActivity)
                activity?.finish()
            }
        }
        viewModel.isExclamationMarkEntered.observe(requireActivity()) { exclamationMark ->
            if (exclamationMark) {
                ToastMessageUtil.showToast(
                    requireContext(),
                    requireContext().resources.getString(R.string.user_input_regular_expression)
                )
            }
        }
    }

    companion object {
        private const val REQUEST_CREATE_EX = 3
    }
}
