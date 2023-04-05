package com.sopt.peekabookaos.presentation.login

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityLoginBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_login) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_socialLoginFragment_to_userInputFragment)
    }
}
