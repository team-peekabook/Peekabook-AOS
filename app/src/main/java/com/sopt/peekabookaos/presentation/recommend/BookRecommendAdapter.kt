package com.sopt.peekabookaos.presentation.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.Recommend
import com.sopt.peekabookaos.databinding.ItemRecommendBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class BookRecommendAdapter :
    ListAdapter<Recommend, BookRecommendAdapter.BookRecommendationViewHolder>(
        recommendationDiffUtil
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookRecommendationViewHolder {
        val itemRecommendRecommendedBinding =
            ItemRecommendBinding.inflate(
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

    class BookRecommendationViewHolder(private val binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(bookRecommendation: Recommend) {
            binding.data = bookRecommendation
        }
    }

    companion object {
        private val recommendationDiffUtil = ItemDiffCallback<Recommend>(
            onItemsTheSame = { old, new -> old.bookTitle == new.bookTitle },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
