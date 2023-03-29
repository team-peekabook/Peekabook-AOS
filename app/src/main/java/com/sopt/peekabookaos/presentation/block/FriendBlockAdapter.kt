package com.sopt.peekabookaos.presentation.block

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemBlockBinding
import com.sopt.peekabookaos.domain.entity.Recommend
import com.sopt.peekabookaos.util.ItemDiffCallback

class FriendBlockAdapter :
    ListAdapter<Recommend, FriendBlockAdapter.FriendBlockViewHolder>(
        recommendationDiffUtil
    ) {
    class FriendBlockViewHolder(private val binding: ItemBlockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(bookRecommendation: Recommend) {
            binding.data = bookRecommendation
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendBlockAdapter.FriendBlockViewHolder {
        val itemRecommendRecommendedBinding =
            ItemBlockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FriendBlockAdapter.FriendBlockViewHolder(
            itemRecommendRecommendedBinding
        )
    }

    override fun onBindViewHolder(
        holder: FriendBlockAdapter.FriendBlockViewHolder,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val recommendationDiffUtil = ItemDiffCallback<Recommend>(
            onItemsTheSame = { old, new -> old.bookTitle == new.bookTitle },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
