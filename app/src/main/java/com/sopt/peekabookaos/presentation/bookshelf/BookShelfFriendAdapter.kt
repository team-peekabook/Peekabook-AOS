package com.sopt.peekabookaos.presentation.bookshelf

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.FriendUser
import com.sopt.peekabookaos.databinding.ItemBookshelfUserProfileBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback
class BookShelfFriendAdapter(
    private val clickListener: ItemClickListener<FriendUser>
) : ListAdapter<FriendUser, BookShelfFriendAdapter.FriendProfileViewHolder>(DIFF_CALLBACK) {
    private var selectedPosition = RecyclerView.NO_POSITION
    private var prePosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendProfileViewHolder {
        val itemBookshelfFriendBinding =
            ItemBookshelfUserProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FriendProfileViewHolder(itemBookshelfFriendBinding)
    }

    override fun onBindViewHolder(holder: FriendProfileViewHolder, position: Int) {
        holder.onBind(getItem(position), clickListener)
        prePosition = selectedPosition
        holder.binding.ivItemBookshelfFriendProfileRedline.isVisible =
            (position == selectedPosition && position != RecyclerView.NO_POSITION)
    }

    fun clearSelection() {
        prePosition = selectedPosition
        selectedPosition = RecyclerView.NO_POSITION
        notifyItemChanged(prePosition)
    }

    fun updateSelectedPosition(position: Int) {
        Log.e("kang","first $position")
        selectedPosition = position
        notifyItemChanged(selectedPosition)
        notifyItemChanged(prePosition)
    }


    class FriendProfileViewHolder(
        val binding: ItemBookshelfUserProfileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: FriendUser, itemClickListener: ItemClickListener<FriendUser>) {
            binding.data = data
            binding.root.setOnClickListener {
                itemClickListener.onClick(absoluteAdapterPosition, data)
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = ItemDiffCallback<FriendUser>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

fun interface ItemClickListener<T> {
    fun onClick(pos: Int, item: T)
}
