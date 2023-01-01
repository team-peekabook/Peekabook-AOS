package com.sopt.peekabookaos.presentation.registerBook

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityRegisterBookBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterBookActivity :
    BindingActivity<ActivityRegisterBookBinding>(R.layout.activity_register_book) {
    private val registerBookViewModel: RegisterBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = registerBookViewModel
    }
}
