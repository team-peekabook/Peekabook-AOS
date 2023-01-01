package com.sopt.peekabookaos.presentation.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.bookshelf.FriendProfileData
import com.sopt.peekabookaos.databinding.ItemBookshelfFriendProfileBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class BookShelfFriendAdapter :
    ListAdapter<FriendProfileData, BookShelfFriendAdapter.FriendProfileViewHolder>(friendDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendProfileViewHolder {
        val itemBookshelfFriendBinding =
            ItemBookshelfFriendProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FriendProfileViewHolder(itemBookshelfFriendBinding)
    }

    override fun onBindViewHolder(holder: FriendProfileViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class FriendProfileViewHolder(private val binding: ItemBookshelfFriendProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: FriendProfileData) {
            binding.data = data
        }
    }

    companion object {
        val friendDiffUtil = ItemDiffCallback<FriendProfileData>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}