package com.sopt.peekabookaos.presentation.forceUpdate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityForceUpdateBinding
import com.sopt.peekabookaos.domain.entity.Version
import com.sopt.peekabookaos.presentation.splash.SplashActivity.Companion.LATEST_VERSION
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.getParcelable

class ForceUpdateActivity :
    BindingActivity<ActivityForceUpdateBinding>(R.layout.activity_force_update) {
    private lateinit var intentToPlayStore: Intent
    private val viewModel by viewModels<ForceUpdateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        getLatestVersion()
        initUpdateBtnClickListener()
    }

    private fun initUpdateBtnClickListener() {
        binding.btnForceUpdate.setOnClickListener {
            intentToPlayStore = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(PEEKABOOKSTORE)
            )
            startActivity(intentToPlayStore)
        }
    }

    private fun getLatestVersion() {
        intent.getParcelable(LATEST_VERSION, Version::class.java)
            ?.let { viewModel.getLatestVersion(it) }
    }

    companion object {
        private const val PEEKABOOKSTORE =
            "https://play.google.com/store/apps/details?id=com.sopt.peekabookaos&hl=ko-KR"
    }
}
