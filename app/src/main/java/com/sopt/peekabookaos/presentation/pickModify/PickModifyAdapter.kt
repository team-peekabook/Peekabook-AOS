package com.sopt.peekabookaos.presentation.pickModify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.Shelf
import com.sopt.peekabookaos.databinding.ItemPickModifyBinding
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback

class PickModifyAdapter :
    androidx.recyclerview.widget.ListAdapter<Shelf, PickModifyAdapter.PickShelfViewHolder>(
        myShelfDiffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickShelfViewHolder {
        val itemPickModifyBinding =
            ItemPickModifyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PickShelfViewHolder(itemPickModifyBinding)
    }

    override fun onBindViewHolder(holder: PickShelfViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class PickShelfViewHolder(private val binding: ItemPickModifyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Shelf) {
            binding.data = data
        }
    }

    companion object {
        val myShelfDiffUtil = ItemDiffCallback<Shelf>(
            onItemsTheSame = { old, new -> old.book == new.book },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}