package com.sopt.peekabookaos.presentation.pickModify

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.Shelf
import com.sopt.peekabookaos.databinding.ItemPickModifyBinding
import com.sopt.peekabookaos.presentation.bookshelf.ItemClickListener
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class PickModifyAdapter(private val clickListener: ItemClickListener<Shelf>) :
    androidx.recyclerview.widget.ListAdapter<Shelf, PickModifyAdapter.PickShelfViewHolder>(
        DIFF_CALLBACK
    ) {
    private var selectedPosition = RecyclerView.NO_POSITION
    private var unSelectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickShelfViewHolder {
        val itemPickModifyBinding =
            ItemPickModifyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PickShelfViewHolder(itemPickModifyBinding)
    }

    override fun onBindViewHolder(holder: PickShelfViewHolder, position: Int) {
        holder.onBind(getItem(position), clickListener)
        holder.binding.ivItemBookshelfShelfSelect.isVisible =
            (position == selectedPosition && position != RecyclerView.NO_POSITION)
        holder.binding.tvItemBookshelfShelfPick.isVisible =
            (position == selectedPosition && position != RecyclerView.NO_POSITION)
        holder.binding.tvItemBookshelfShelfPick.text = position.toString()
    }

    fun updateSelectedPosition(position: Int) {
        selectedPosition = position
        notifyItemChanged(selectedPosition)
        Log.e("kang", "ad selec: $selectedPosition")
    }

    fun updateUnSelectedPosition(position: Int) {
        unSelectedPosition = position
        selectedPosition = RecyclerView.NO_POSITION
        notifyItemChanged(unSelectedPosition)
        Log.e("kang", "ad unselec: $unSelectedPosition")
    }

    class PickShelfViewHolder(val binding: ItemPickModifyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Shelf, itemClickListener: ItemClickListener<Shelf>) {
            binding.data = data
            binding.root.setOnClickListener {
                itemClickListener.onClick(absoluteAdapterPosition, data)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            ItemDiffCallback<Shelf>(
                onItemsTheSame = { old, new -> old.book == new.book },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
