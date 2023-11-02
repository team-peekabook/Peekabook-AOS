package com.sopt.peekabookaos.presentation.forceUpdate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityForceUpdateBinding
import com.sopt.peekabookaos.util.binding.BindingActivity

class ForceUpdateActivity :
    BindingActivity<ActivityForceUpdateBinding>(R.layout.activity_force_update) {
    lateinit var intentToPlayStore: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnForceUpdate.setOnClickListener {
            intentToPlayStore = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.sopt.peekabookaos&hl=ko-KR")
            )
            startActivity(intentToPlayStore)
        }
    }
}
