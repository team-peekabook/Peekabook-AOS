package com.sopt.peekabookaos.presentation.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.Pick
import com.sopt.peekabookaos.databinding.ItemBookshelfPickBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class BookShelfPickAdapter :
    ListAdapter<Pick, BookShelfPickAdapter.PickViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickViewHolder {
        val itemBookshelfPickBinding =
            ItemBookshelfPickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PickViewHolder(itemBookshelfPickBinding)
    }

    override fun onBindViewHolder(holder: PickViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class PickViewHolder(private val binding: ItemBookshelfPickBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Pick) {
            binding.data = data
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            ItemDiffCallback<Pick>(
                onItemsTheSame = { old, new -> old.idx == new.idx },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
