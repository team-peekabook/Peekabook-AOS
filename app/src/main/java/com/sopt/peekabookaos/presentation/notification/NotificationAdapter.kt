package com.sopt.peekabookaos.presentation.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ItemNotificationBinding
import com.sopt.peekabookaos.domain.entity.Notification
import com.sopt.peekabookaos.util.ItemDiffCallback
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener

class NotificationAdapter(
    private val onNotificationClicked: (Notification) -> Unit
) : ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.binding.clItemNotification.setSingleOnClickListener {
            onNotificationClicked(item)
        }
    }

    inner class NotificationViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Notification) = with(binding) {
            data = item
            tvNotificationDate.text = item.createdAt

            val commentText = buildComment(item)
            when (item.typeId) {
                1, 4 -> {
                    tvNotificationMentionType1.text = commentText
                    tvNotificationMentionType1.isVisible = true
                    tvNotificationMention.isVisible = false
                    tvNotificationBookTitle.isVisible = false
                }

                2, 3 -> {
                    tvNotificationMention.text = commentText
                    tvNotificationMentionType1.isVisible = false
                    tvNotificationMention.isVisible = true
                    tvNotificationBookTitle.isVisible = true
                }
            }
        }

        private fun buildComment(item: Notification): String {
            val ctx = binding.root.context
            return when (item.typeId) {
                1, 4 -> {
                    val resId = if (item.senderName.length <= 5) {
                        R.string.notification_follow_name_short
                    } else {
                        R.string.notification_follow_name_long
                    }
                    ctx.getString(resId, item.senderName)
                }

                2 -> ctx.getString(R.string.notification_recommend, item.senderName)
                3 -> ctx.getString(R.string.notification_add, item.senderName)
                else -> ""
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = ItemDiffCallback<Notification>(
            onItemsTheSame = { old, new -> old.alarmId == new.alarmId },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
