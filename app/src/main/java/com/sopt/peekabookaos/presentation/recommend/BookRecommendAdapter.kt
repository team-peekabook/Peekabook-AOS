package com.sopt.peekabookaos.presentation.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemRecommendBinding
import com.sopt.peekabookaos.domain.entity.Recommend
import com.sopt.peekabookaos.util.ItemDiffCallback

class BookRecommendAdapter(
    private val onClickDelete: (Int) -> Unit
) : ListAdapter<Recommend, BookRecommendAdapter.BookRecommendationViewHolder>(recommendationDiffUtil) {

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
            itemRecommendRecommendedBinding,
            onClickDelete
        )
    }

    override fun onBindViewHolder(holder: BookRecommendationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class BookRecommendationViewHolder(
        private val binding: ItemRecommendBinding,
        private val onClickDelete: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Recommend) {
            binding.data = data
            binding.ivItemRecommendDelete.setOnClickListener {
                onClickDelete(data.recommendId)
            }
        }
    }

    companion object {
        private val recommendationDiffUtil = ItemDiffCallback<Recommend>(
            onItemsTheSame = { old, new -> old.bookTitle == new.bookTitle },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
