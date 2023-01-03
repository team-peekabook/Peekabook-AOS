package com.sopt.peekabookaos.presentation.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.RecommendData
import com.sopt.peekabookaos.databinding.ItemRecommendRecommendedBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class BookRecommendAdapter :
    ListAdapter<RecommendData, BookRecommendAdapter.BookRecommendationViewHolder>(
        recommendationDiffUtil
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookRecommendationViewHolder {
        val itemRecommendRecommendedBinding =
            ItemRecommendRecommendedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return BookRecommendationViewHolder(
            itemRecommendRecommendedBinding
        )
    }

    override fun onBindViewHolder(holder: BookRecommendationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class BookRecommendationViewHolder(private val binding: ItemRecommendRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(bookRecommendation: RecommendData) {
            binding.data = bookRecommendation
        }
    }

    companion object {
        private val recommendationDiffUtil = ItemDiffCallback<RecommendData>(
            onItemsTheSame = { old, new -> old.title == new.title },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
