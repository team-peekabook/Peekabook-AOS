package com.sopt.peekabookaos.presentation.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.Notification
import com.sopt.peekabookaos.databinding.ItemNotificationBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class NotificationAdapter :
    ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemNotificationBinding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(itemNotificationBinding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Notification) {
            binding.data = data
        }
    }

    companion object {
        val DIFF_CALLBACK = ItemDiffCallback<Notification>(
            onItemsTheSame = { old, new -> old.alarmId == new.alarmId },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
