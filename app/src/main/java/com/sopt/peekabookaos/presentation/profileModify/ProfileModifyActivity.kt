package com.sopt.peekabookaos.presentation.profileModify

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityProfileModifyBinding
import com.sopt.peekabookaos.presentation.myPage.MyPageFragment
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileModifyActivity :
    BindingActivity<ActivityProfileModifyBinding>(R.layout.activity_profile_modify) {
    private val viewModel: ProfileModifyViewModel by viewModels()
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.updateProfileImage(uri)
        }
    }
    private var photoURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initEditTextClearFocus()
        initCheckClickListener()
        initBackClickListener()
        initDuplicateClickListener()
        initObserver()
        initAddClickListener()
        initProfileClickListener()
        goToMyPageFragment()
        initBackPressedCallback()
    }

    private fun initBackClickListener() {
        binding.btnProfileModifyBack.setSingleOnClickListener {
            finish()
        }
    }

    private fun goToMyPageFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.cl_profile_modify, MyPageFragment())
            .commit()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goToMyPageFragment()
                }
            }
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clProfileModify.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(this)
            return@setOnTouchListener false
        }
    }

    private fun initAddClickListener() {
        binding.btnProfileModifyAdd.setOnClickListener {
            val profileModifyBottomSheetFragment = ProfileModifyBottomSheetFragment.onItemClick {
                when (it) {
                    0 -> launcher.launch("image/*")
                    1 -> if (checkPermission()) {
                        dispatchTakePictureIntentEx()
                    } else {
                        requestPermission()
                    }
                }
            }
            profileModifyBottomSheetFragment.show(
                supportFragmentManager,
                profileModifyBottomSheetFragment.tag
            )
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            1
        )
    }

    private fun checkPermission(): Boolean {
        return (
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
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
            ToastMessageUtil.showToast(this, "권한 설정되었습니다.")
        } else {
            ToastMessageUtil.showToast(this, "권한 허용이 거부되었습니다.")
        }
    }

    private fun dispatchTakePictureIntentEx() {
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
        return contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CREATE_EX) {
            photoURI?.let { viewModel.updateProfileImage(it) }
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
        binding.btnProfileModifyAdd.setSingleOnClickListener {
            launcher.launch("image/*")
        }
    }

    companion object {
        private const val REQUEST_CREATE_EX = 3
    }
}
