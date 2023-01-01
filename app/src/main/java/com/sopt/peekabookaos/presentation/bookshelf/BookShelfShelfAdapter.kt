package com.sopt.peekabookaos.presentation.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.bookshelf.ShelfData
import com.sopt.peekabookaos.databinding.ItemBookshelfShelfBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class BookShelfShelfAdapter :
    ListAdapter<ShelfData, BookShelfShelfAdapter.MyShelfViewHolder>(myShelfDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyShelfViewHolder {
        val itemBookshelfMyShelfBinding =
            ItemBookshelfShelfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyShelfViewHolder(itemBookshelfMyShelfBinding)
    }

    override fun onBindViewHolder(holder: MyShelfViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class MyShelfViewHolder(private val binding: ItemBookshelfShelfBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ShelfData) {
            binding.data = data
        }
    }

    companion object {
        val myShelfDiffUtil = ItemDiffCallback<ShelfData>(
            onItemsTheSame = { old, new -> old.book == new.book },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
