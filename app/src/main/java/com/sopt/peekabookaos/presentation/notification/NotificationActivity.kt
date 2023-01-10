package com.sopt.peekabookaos.presentation.notification

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.Notification
import com.sopt.peekabookaos.databinding.ActivityNotificationBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NotificationActivity :
    BindingActivity<ActivityNotificationBinding>(R.layout.activity_notification) {
    private lateinit var notifyAdapter: NotificationAdapter
    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initIsServerObserver()
        initAdapter()
        initCloseClickListener()
    }

    private fun initIsServerObserver() {
        viewModel.isServerStatus.observe(this) { success ->
            if (success) {
                notifyAdapter.submitList(viewModel.notificationData.value)
                Timber.tag("kang").d("${viewModel.notificationData.value}")
            }
        }
    }

    private fun initAdapter() {
        notifyAdapter = NotificationAdapter { _, item ->
            notifyAdapter.setComment(initCommentString(item))
        }
        binding.rvNotification.adapter = notifyAdapter
    }

    private fun initCloseClickListener() {
        binding.ivNotificationClose.setOnClickListener {
            finish()
        }
    }

    private fun initCommentString(item: Notification): String {
        lateinit var comment: String
        when (item.typeId) {
            1 -> {
                comment = if (item.senderName.length <= 5) {
                    resources.getString(R.string.notification_follow_name_short)
                } else {
                    resources.getString(R.string.notification_follow_name_long)
                }
            }
            2 -> comment = resources.getString(R.string.notification_recommend)

            3 -> comment = resources.getString(R.string.notification_add)
        }
        return comment
    }
}
