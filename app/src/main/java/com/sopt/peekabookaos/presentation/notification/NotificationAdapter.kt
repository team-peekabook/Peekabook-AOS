package com.sopt.peekabookaos.presentation.notification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemNotificationBinding
import com.sopt.peekabookaos.domain.entity.Notification
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class NotificationAdapter(
    private val itemStringListener: ItemStringListener<Notification>
) :
    ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(DIFF_CALLBACK) {

    private val commentList = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemNotificationBinding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(itemNotificationBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.onBind(getItem(position), itemStringListener)
        when (getItem(position).typeId) {
            1 -> {
                with(holder.binding) {
                    tvNotificationMentionType1.text = String.format(
                        commentList[position],
                        getItem(position).senderName
                    )
                    tvNotificationMentionType1.isVisible = true
                    tvNotificationMention.isVisible = false
                    tvNotificationBookTitle.isVisible = false
                }
            }
            2 -> {
                with(holder.binding) {
                    tvNotificationMentionType1.isVisible = false
                    tvNotificationMention.isVisible = true
                    tvNotificationBookTitle.isVisible = true
                }
            }
            3 -> {
                with(holder.binding) {
                    tvNotificationMentionType1.isVisible = false
                    tvNotificationMention.isVisible = true
                    tvNotificationBookTitle.isVisible = true
                }
            }
        }
        with(holder.binding) {
            tvNotificationMention.text = String.format(
                commentList[position],
                getItem(position).senderName
            )
            tvNotificationDate.text =
                getItem(position).createdAt.split("-")[1] + "월 " + getItem(position).createdAt.split(
                "-"
            )[2] + "일"
        }
    }

    fun setComment(comment: String) {
        commentList.add(comment)
    }

    class NotificationViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Notification, itemStringListener: ItemStringListener<Notification>) {
            binding.data = data
            itemStringListener.getStringResource(absoluteAdapterPosition, data)
        }
    }

    companion object {
        private val DIFF_CALLBACK = ItemDiffCallback<Notification>(
            onItemsTheSame = { old, new -> old.alarmId == new.alarmId },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

fun interface ItemStringListener<T> {
    fun getStringResource(pos: Int, item: T)
}
