package com.sopt.peekabookaos.presentation.networkError

import android.content.Intent
import android.os.Bundle
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityNetworkErrorBinding
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.isNetworkConnected
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetworkErrorActivity :
    BindingActivity<ActivityNetworkErrorBinding>(R.layout.activity_network_error) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetworkConnect()
    }

    private fun checkNetworkConnect() {
        binding.btnNetworkErrorRetry.setOnClickListener {
            if (isNetworkConnected()) {
                // TODO by 이빵주 : 로그인 or 메인으로 분기처리하기
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(0, 0)
                finish()
            } else {
                ToastMessageUtil.showToast(
                    this@NetworkErrorActivity,
                    getString(R.string.network_error_toast)
                )
            }
        }
    }
}
