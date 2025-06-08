package com.sopt.peekabookaos.presentation.notification

import android.os.Bundle
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityNotificationBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.activityCloseTransition
import com.sopt.peekabookaos.util.extensions.activityOpenTransition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity :
    BindingActivity<ActivityNotificationBinding>(R.layout.activity_notification) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityOpenTransition(R.anim.anim_from_bottom, R.anim.anim_to_top)
    }

    override fun finish() {
        super.finish()
        activityCloseTransition(R.anim.anim_from_left, R.anim.anim_to_right)
    }
}
