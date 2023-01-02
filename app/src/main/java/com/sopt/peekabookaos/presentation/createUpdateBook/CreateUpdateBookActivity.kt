package com.sopt.peekabookaos.presentation.createUpdateBook

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityCreateUpdateBookBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUpdateBookActivity :
    BindingActivity<ActivityCreateUpdateBookBinding>(R.layout.activity_create_update_book) {
    private val registerBookViewModel: CreateUpdateBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = registerBookViewModel
        initCloseBtnOnClickListener()
        initSaveBtnOnClickListener()
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnRegisterBookClose.setOnClickListener {
            finish()
        }
    }

    private fun initSaveBtnOnClickListener() {
        binding.btnRegisterBookSave.setOnClickListener {
            /* 추후 서버 연결 시에 추가 예정 */
        }
    }
}
