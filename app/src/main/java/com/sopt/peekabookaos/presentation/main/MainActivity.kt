package com.sopt.peekabookaos.presentation.main

import android.os.Bundle
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityMainBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
