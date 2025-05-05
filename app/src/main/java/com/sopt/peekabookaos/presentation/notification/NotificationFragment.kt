package com.sopt.peekabookaos.presentation.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentNotificationBinding
import com.sopt.peekabookaos.domain.entity.Notification
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :
    BindingFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {

    private val viewModel: NotificationViewModel by viewModels()
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
            onNotificationClicked = { typeId ->
                initNotificationItemClickListener(typeId)
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
            1 -> if (item.senderName.length <= 5) {
                getString(R.string.notification_follow_name_short)
            } else {
                getString(R.string.notification_follow_name_long)
            }

            2 -> getString(R.string.notification_recommend)
            3 -> getString(R.string.notification_add)
            else -> ""
        }

    private fun initNotificationItemClickListener(typeId: Int) {
        when (typeId) {
            /** 1: 팔로우(맞팔), 2: 추천, 3: 팔로우 한 사람이 책 추가, 4: 팔로우(선팔) */
            1, 3, 4 -> findNavController().navigate(R.id.action_notificationFragment_to_notificationBookshelfFragment)
            2 -> findNavController().navigate(R.id.action_notificationFragment_to_notificationRecommendedFragment)
        }
    }
}
