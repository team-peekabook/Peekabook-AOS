package com.sopt.peekabookaos.presentation.userInput

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
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import java.text.SimpleDateFormat
import java.util.Date

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
        binding.btnUserInputAdd.setOnClickListener {
            val userInputBottomSheetFragment = UserInputBottomSheetFragment.onItemClick {
                when (it) {
                    0 -> launcher.launch("image/*")
                    1 -> if (checkPermission()) { // 카메라 바텀시트 눌렀을 때, 퍼미션 받았는지 체크하고 받았다면
                        dispatchTakePictureIntentEx() // 카메라 띄우고
                    } else {
                        requestCameraPermission() // 안받았으면 권한 요청으로
                    }
                }
            }
            userInputBottomSheetFragment.show(
                parentFragmentManager,
                userInputBottomSheetFragment.tag
            )
        }
    }

    private fun requestCameraPermission() { // 권한 요청 함수
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
                permissions[CAMERA] != null && permissions[CAMERA]!! // 카메라 권한 여부
            val isWriteStoragePermissionGranted =
                permissions[WRITE_EXTERNAL_STORAGE] != null && permissions[WRITE_EXTERNAL_STORAGE]!! // 내부 저장소 쓰기 권한 여부
            val isReadStoragePermissionGranted =
                permissions[READ_EXTERNAL_STORAGE] != null && permissions[READ_EXTERNAL_STORAGE]!! // 내부 저장소 읽기 권한 여부
            if (isCameraPermissionGranted && isWriteStoragePermissionGranted && isReadStoragePermissionGranted) { // 전부 다 권한 허가 됐다면
                ToastMessageUtil.showToast(
                    requireContext(),
                    getString(R.string.user_input_permission) // 허가 됐다는 문구
                )
                dispatchTakePictureIntentEx() // 카메라 실행
            } else {
                ToastMessageUtil.showToast(
                    requireContext(),
                    getString(R.string.user_input_request_permission) // 권한 거부되었을 경우
                )
            }
        }

    private fun checkPermission(): Boolean { // 권한 요청 함수
        return (
            ContextCompat.checkSelfPermission(requireActivity(), CAMERA)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            )
    }

    private fun dispatchTakePictureIntentEx() { // 카메라 호출 함수
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
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
        return context?.contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
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
        }
    }

    private fun initObserver() {
        viewModel.nickname.observe(requireActivity()) {
            viewModel.updateCheckButtonState()
            viewModel.updateWritingState()
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
    }

    companion object {
        private const val REQUEST_CREATE_EX = 3
    }
}
