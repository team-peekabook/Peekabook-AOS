package com.sopt.peekabookaos.presentation.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.User
import com.sopt.peekabookaos.databinding.ItemBookshelfFriendProfileBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class BookShelfFriendAdapter(
    private val clickListener: ItemClickListener<User>
) :
    ListAdapter<User, BookShelfFriendAdapter.FriendProfileViewHolder>(friendDiffUtil) {

    private lateinit var itemBookshelfFriendBinding: ItemBookshelfFriendProfileBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendProfileViewHolder {
        itemBookshelfFriendBinding =
            ItemBookshelfFriendProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FriendProfileViewHolder(itemBookshelfFriendBinding)
    }

    override fun onBindViewHolder(holder: FriendProfileViewHolder, position: Int) {
        holder.onBind(getItem(position))
        itemBookshelfFriendBinding.root.setOnClickListener {
            clickListener.onClick(position, getItem(position))
        }
    }

    class FriendProfileViewHolder(private val binding: ItemBookshelfFriendProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: User) {
            binding.data = data
        }
    }

    companion object {
        val friendDiffUtil = ItemDiffCallback<User>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

interface ItemClickListener<T> {
    fun onClick(pos: Int, item: T)
}
