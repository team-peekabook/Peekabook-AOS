package com.sopt.peekabookaos.presentation.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentNotificationBinding
import com.sopt.peekabookaos.domain.entity.Notification
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.NOTIFICATION
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :
    BindingFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {

    private val viewModel: NotificationViewModel by viewModels()
    private val bundle = Bundle()
    private lateinit var notifyAdapter: NotificationAdapter
    private lateinit var itemDeco: NotificationDecoration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initIsServerObserver()
        initItemDecoration()
        initAdapter()
        initCloseClickListener()
    }

    private fun initIsServerObserver() {
        viewModel.isServerStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                notifyAdapter.submitList(viewModel.notificationData.value)
            }
        }
    }

    private fun initItemDecoration() {
        itemDeco = NotificationDecoration(requireContext())
        binding.rvNotification.addItemDecoration(itemDeco)
    }

    private fun initAdapter() {
        notifyAdapter = NotificationAdapter(
            itemStringListener = { _, item ->
                notifyAdapter.setComment(initCommentString(item))
            },
            onNotificationClicked = { item ->
                initNotificationItemClickListener(item)
            }
        )
        binding.rvNotification.adapter = notifyAdapter
    }

    private fun initCloseClickListener() {
        binding.ivNotificationClose.setSingleOnClickListener {
            activity?.finish()
        }
    }

    private fun initCommentString(item: Notification): String =
        when (item.typeId) {
            FOLLOW_EACH_OTHER, FOLLOWER_ONLY -> if (item.senderName.length <= 5) {
                getString(R.string.notification_follow_name_short)
            } else {
                getString(R.string.notification_follow_name_long)
            }

            BOOK_RECOMMENDED -> getString(R.string.notification_recommend)
            BOOK_ADDED -> getString(R.string.notification_add)
            else -> ""
        }

    private fun initNotificationItemClickListener(item: Notification) {
        when (item.typeId) {
            FOLLOW_EACH_OTHER, BOOK_ADDED, FOLLOWER_ONLY -> {
                findNavController().navigate(
                    R.id.action_notificationFragment_to_notificationBookshelfFragment,
                    bundle.apply {
                        putParcelable(NOTIFICATION, item)
                    }
                )
            }

            BOOK_RECOMMENDED -> {
                findNavController().navigate(R.id.action_notificationFragment_to_notificationRecommendedFragment)
            }
        }
    }

    companion object {
        const val FOLLOW_EACH_OTHER = 1
        const val BOOK_RECOMMENDED = 2
        const val BOOK_ADDED = 3
        const val FOLLOWER_ONLY = 4
    }
}
