package com.sopt.peekabookaos.presentation.bookshelf

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.FriendUser
import com.sopt.peekabookaos.databinding.ItemBookshelfUserProfileBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class BookShelfFriendAdapter(
    private val clickListener: ItemClickListener<FriendUser>
) :
    ListAdapter<FriendUser, BookShelfFriendAdapter.FriendProfileViewHolder>(friendDiffUtil) {

    private lateinit var itemBookshelfFriendBinding: ItemBookshelfUserProfileBinding
    private var selectPosition = -1
    private var prePosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendProfileViewHolder {
        itemBookshelfFriendBinding =
            ItemBookshelfUserProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FriendProfileViewHolder(itemBookshelfFriendBinding)
    }

    override fun onBindViewHolder(holder: FriendProfileViewHolder, position: Int) {
        holder.onBind(getItem(position))
        if (selectPosition == holder.absoluteAdapterPosition) {
            holder.binding.ivItemBookshelfFriendProfileRedline.visibility = View.VISIBLE
        } else {
            holder.binding.ivItemBookshelfFriendProfileRedline.visibility = View.INVISIBLE
        }
        itemBookshelfFriendBinding.root.setOnClickListener {
            clickListener.onClick(
                holder.absoluteAdapterPosition,
                getItem(holder.absoluteAdapterPosition)
            )
            prePosition = selectPosition
            selectPosition = holder.absoluteAdapterPosition
            if (prePosition != -1)
                notifyItemChanged(prePosition, 1)
            if (selectPosition != -1)
                notifyItemChanged(selectPosition, 1)
            Log.d(
                "kang",
                "$position : position, ${holder.absoluteAdapterPosition} : holder.absoluteAdapterPosition $prePosition :pre $position :position"
            )
        }
    }

    fun clearSelection(pos: Int) {
        notifyItemChanged(prePosition, 1)
        notifyItemChanged(selectPosition, 1)
        prePosition = selectPosition
        selectPosition = -1
    }

    class FriendProfileViewHolder(val binding: ItemBookshelfUserProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: FriendUser) {
            binding.data = data
        }
    }

    companion object {
        val friendDiffUtil = ItemDiffCallback<FriendUser>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

interface ItemClickListener<T> {
    fun onClick(pos: Int, item: T)
}
