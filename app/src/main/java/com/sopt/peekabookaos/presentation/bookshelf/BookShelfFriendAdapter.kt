package com.sopt.peekabookaos.presentation.bookshelf

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.User
import com.sopt.peekabookaos.databinding.ItemBookshelfUserProfileBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class BookShelfFriendAdapter(
    private val clickListener: ItemClickListener<User>
) :
    ListAdapter<User, BookShelfFriendAdapter.FriendProfileViewHolder>(friendDiffUtil) {

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

    override fun onBindViewHolder(holder: FriendProfileViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.onBind(getItem(position))
        if(selectPosition == position){
            holder.binding.ivItemBookshelfFriendProfileRedline.visibility = View.VISIBLE
        }else{
            holder.binding.ivItemBookshelfFriendProfileRedline.visibility = View.INVISIBLE
        }
        itemBookshelfFriendBinding.root.setOnClickListener {
            clickListener.onClick(position, getItem(position))
            changeRedItem(position)
        }
    }

    fun changeRedItem(position: Int){
        prePosition = selectPosition
        selectPosition = position
        notifyItemChanged(prePosition)
        notifyItemChanged(selectPosition)
    }

    class FriendProfileViewHolder(val binding: ItemBookshelfUserProfileBinding) :
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
