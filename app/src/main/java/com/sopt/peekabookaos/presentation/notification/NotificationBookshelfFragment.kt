package com.sopt.peekabookaos.presentation.notification

import android.os.Bundle
import android.view.View
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentNotificationBookshelfBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationBookshelfFragment : BindingFragment<FragmentNotificationBookshelfBinding>(R.layout.fragment_notification_bookshelf) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
