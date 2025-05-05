package com.sopt.peekabookaos.presentation.notificationBookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemBookshelfShelfBinding
import com.sopt.peekabookaos.domain.entity.Books
import com.sopt.peekabookaos.presentation.bookshelf.ItemClickListener
import com.sopt.peekabookaos.util.ItemDiffCallback

class NotificationBookShelfShelfAdapter(private val clickListener: ItemClickListener<Books>) :
    ListAdapter<Books, NotificationBookShelfShelfAdapter.MyShelfViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyShelfViewHolder {
        val itemBookshelfMyShelfBinding =
            ItemBookshelfShelfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyShelfViewHolder(itemBookshelfMyShelfBinding)
    }

    override fun onBindViewHolder(holder: MyShelfViewHolder, position: Int) {
        holder.onBind(getItem(position), clickListener)
    }

    class MyShelfViewHolder(private val binding: ItemBookshelfShelfBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Books, itemClickListener: ItemClickListener<Books>) {
            binding.data = data
            binding.root.setOnClickListener {
                itemClickListener.onClick(absoluteAdapterPosition, data)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = ItemDiffCallback<Books>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
