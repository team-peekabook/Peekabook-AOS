package com.sopt.peekabookaos.presentation.search.book

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ItemSearchBookBinding
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.util.ItemDiffCallback

class SearchBookAdapter(
    private val onClickBook: (Book) -> Unit,
    private val text: String
) : ListAdapter<Book, RecyclerView.ViewHolder>(SEARCH_BOOK_DIFF_CALLBACK) {

    var showFooter: Boolean = false
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged() // 강제 리바인딩
        }

    override fun getItemCount(): Int = currentList.size + if (showFooter) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (showFooter && position == currentList.size) VIEW_TYPE_FOOTER else VIEW_TYPE_BOOK
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_BOOK) {
            val binding = ItemSearchBookBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            SearchBookViewHolder(binding, onClickBook, text)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_book_no_book, parent, false)
            FooterViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchBookViewHolder -> holder.onBind(getItem(position))
            is FooterViewHolder -> holder.bind()
        }
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

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val footerTextView: View = itemView.findViewById(R.id.tv_item_search_book_no_book)

        fun bind() {
            footerTextView.setOnClickListener {
                val url = "https://walla.my/v/1g1JvcCRxDwmAeTM6xZw"
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_BOOK = 0
        private const val VIEW_TYPE_FOOTER = 1

        private val SEARCH_BOOK_DIFF_CALLBACK =
            ItemDiffCallback<Book>(
                onItemsTheSame = { old, new -> old.bookTitle == new.bookTitle },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
