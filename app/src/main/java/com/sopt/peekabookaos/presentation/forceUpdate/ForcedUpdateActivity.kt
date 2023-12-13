package com.sopt.peekabookaos.presentation.forceUpdate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityForcedUpdateBinding
import com.sopt.peekabookaos.domain.entity.UpdateInformation
import com.sopt.peekabookaos.presentation.splash.SplashActivity.Companion.UPDATE_INFORMATION
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.getParcelable
import com.sopt.peekabookaos.util.extensions.initBackPressedCallback
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import kotlin.system.exitProcess

class ForcedUpdateActivity :
    BindingActivity<ActivityForcedUpdateBinding>(R.layout.activity_forced_update) {
    private val viewModel by viewModels<ForcedUpdateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        initBackPressedCallback()
        getUpdateInformation()
        initUpdateBtnClickListener()
    }

    private fun initUpdateBtnClickListener() {
        binding.btnForceUpdate.setSingleOnClickListener {
            val intentToPlayStore = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.force_update_store_link))
            )
            startActivity(intentToPlayStore)
        }
    }

    private fun getUpdateInformation() {
        intent.getParcelable(UPDATE_INFORMATION, UpdateInformation::class.java)
            ?.let { viewModel.setUpdateInformation(it) }
    }
}
