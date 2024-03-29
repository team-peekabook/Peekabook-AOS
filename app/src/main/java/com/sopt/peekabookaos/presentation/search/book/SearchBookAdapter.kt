package com.sopt.peekabookaos.presentation.search.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemSearchBookBinding
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.util.ItemDiffCallback

class SearchBookAdapter(
    private val onClickBook: (Book) -> Unit,
    private val text: String
) : ListAdapter<Book, SearchBookAdapter.SearchBookViewHolder>(SEARCH_BOOK_DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBookViewHolder {
        val binding = ItemSearchBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchBookViewHolder(binding, onClickBook, text)
    }

    override fun onBindViewHolder(holder: SearchBookViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class SearchBookViewHolder(
        private val binding: ItemSearchBookBinding,
        private val onClickBook: (Book) -> Unit,
        private val text: String
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Book) {
            binding.data = data
            binding.tvItemSearchBookAdd.text = text
            binding.clItemSearchBook.setOnClickListener {
                onClickBook(data)
            }
        }
    }

    companion object {
        private val SEARCH_BOOK_DIFF_CALLBACK =
            ItemDiffCallback<Book>(
                onItemsTheSame = { old, new -> old.bookTitle == new.bookTitle },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
