package com.sopt.peekabookaos.presentation.login

import android.os.Bundle
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityLoginBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
