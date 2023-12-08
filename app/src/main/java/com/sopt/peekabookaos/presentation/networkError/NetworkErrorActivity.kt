package com.sopt.peekabookaos.presentation.networkError

import android.content.Intent
import android.os.Bundle
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityNetworkErrorBinding
import com.sopt.peekabookaos.presentation.splash.SplashActivity
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.activityOpenTransition
import com.sopt.peekabookaos.util.extensions.initBackPressedCallback
import com.sopt.peekabookaos.util.extensions.isNetworkConnected
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetworkErrorActivity :
    BindingActivity<ActivityNetworkErrorBinding>(R.layout.activity_network_error) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetworkConnect()
        initBackPressedCallback()
    }

    private fun checkNetworkConnect() {
        binding.btnNetworkErrorRetry.setOnClickListener {
            if (isNetworkConnected()) {
                startActivity(
                    Intent(this, SplashActivity::class.java)
                        .putExtra(LOCATION, NETWORK_ERROR)
                )
                activityOpenTransition(0, 0)
                finishAffinity()
            } else {
                ToastMessageUtil.showToast(
                    this@NetworkErrorActivity,
                    getString(R.string.network_error_toast)
                )
            }
        }
    }

    companion object {
        const val LOCATION = "location"
        const val NETWORK_ERROR = "network error"
    }
}
