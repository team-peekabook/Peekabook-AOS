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
    private val createUpdateBookViewModel: CreateUpdateBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = createUpdateBookViewModel
        initCloseBtnOnClickListener()
        initSaveBtnOnClickListener()
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnCreateUpdateBookClose.setOnClickListener {
            finish()
        }
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnCreateUpdateBookClose.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val LOCATION = "location"
        const val UPDATE = "update"
        const val CREATE = "create"

    }
}
