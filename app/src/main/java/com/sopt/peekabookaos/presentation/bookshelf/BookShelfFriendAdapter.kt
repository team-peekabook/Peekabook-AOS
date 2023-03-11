package com.sopt.peekabookaos.presentation.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ItemBookshelfUserProfileBinding
import com.sopt.peekabookaos.domain.entity.FriendList
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class BookShelfFriendAdapter(
    private val clickListener: ItemClickListener<FriendList>
) : ListAdapter<FriendList, BookShelfFriendAdapter.FriendProfileViewHolder>(DIFF_CALLBACK) {
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
        if (position == selectedPosition && position != RecyclerView.NO_POSITION) {
            holder.binding.tvItemBookshelfFriendProfile.setTextAppearance(R.style.S1Bd)
        } else {
            holder.binding.tvItemBookshelfFriendProfile.setTextAppearance(R.style.S2Md)
        }
    }

    fun clearSelection() {
        prePosition = selectedPosition
        selectedPosition = RecyclerView.NO_POSITION
        notifyItemChanged(prePosition)
    }

    fun updateSelectedPosition(position: Int) {
        selectedPosition = position
        notifyItemChanged(selectedPosition)
        notifyItemChanged(prePosition)
    }

    class FriendProfileViewHolder(
        val binding: ItemBookshelfUserProfileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: FriendList, itemClickListener: ItemClickListener<FriendList>) {
            binding.data = data
            binding.root.setOnClickListener {
                itemClickListener.onClick(absoluteAdapterPosition, data)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = ItemDiffCallback<FriendList>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

fun interface ItemClickListener<T> {
    fun onClick(pos: Int, item: T)
}
