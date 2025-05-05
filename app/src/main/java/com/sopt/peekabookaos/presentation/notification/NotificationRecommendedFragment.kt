package com.sopt.peekabookaos.presentation.notification

import android.os.Bundle
import android.view.View
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentNotificationRecommendedBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationRecommendedFragment : BindingFragment<FragmentNotificationRecommendedBinding>(R.layout.fragment_notification_recommended) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
