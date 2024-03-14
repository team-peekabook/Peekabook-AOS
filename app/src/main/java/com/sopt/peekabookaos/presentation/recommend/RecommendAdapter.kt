package com.sopt.peekabookaos.presentation.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemRecommendBinding
import com.sopt.peekabookaos.domain.entity.Recommend
import com.sopt.peekabookaos.util.ItemDiffCallback

class RecommendAdapter(
    private val onClickDelete: (Int) -> Unit
) : ListAdapter<Recommend, RecommendAdapter.RecommendViewHolder>(recommendDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendViewHolder {
        val itemRecommendBinding =
            ItemRecommendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RecommendViewHolder(
            itemRecommendBinding,
            onClickDelete
        )
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class RecommendViewHolder(
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
        private val recommendDiffUtil = ItemDiffCallback<Recommend>(
            onItemsTheSame = { old, new -> old.bookTitle == new.bookTitle },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
