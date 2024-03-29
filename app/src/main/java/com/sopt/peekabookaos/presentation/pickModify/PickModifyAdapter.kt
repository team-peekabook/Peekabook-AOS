package com.sopt.peekabookaos.presentation.pickModify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemPickModifyBinding
import com.sopt.peekabookaos.domain.entity.Picks
import com.sopt.peekabookaos.presentation.bookshelf.ItemClickListener
import com.sopt.peekabookaos.util.ItemDiffCallback

class PickModifyAdapter(
    private val clickListener: ItemClickListener<Picks>
) :
    androidx.recyclerview.widget.ListAdapter<Picks, PickModifyAdapter.PickShelfViewHolder>(
        DIFF_CALLBACK
    ) {
    private var selectedPositionSet: LinkedHashSet<Int>? = linkedSetOf()
    private var initState = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickShelfViewHolder {
        val itemPickModifyBinding =
            ItemPickModifyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PickShelfViewHolder(itemPickModifyBinding)
    }

    override fun onBindViewHolder(holder: PickShelfViewHolder, position: Int) {
        holder.onBind(getItem(position), clickListener)
        if (initState) initSelectedPositionSet(getItem(position), position)
        holder.binding.ivItemBookshelfShelfSelect.isVisible =
            (selectedPositionSet?.contains(position) == true && position != RecyclerView.NO_POSITION)
        holder.binding.tvItemBookshelfShelfPick.isVisible =
            (selectedPositionSet?.contains(position) == true && position != RecyclerView.NO_POSITION)
    }

    fun updateSelectedPosition(position: Int, state: Boolean) {
        initState = false
        if (state) {
            selectedPositionSet?.add(position)
            for (pos in selectedPositionSet!!) {
                notifyItemChanged(pos)
            }
        } else {
            selectedPositionSet?.remove(position)
            notifyItemChanged(position)
            for (pos in selectedPositionSet!!) {
                notifyItemChanged(pos)
            }
        }
    }

    private fun initSelectedPositionSet(item: Picks, position: Int) {
        if (item.pickIndex != 0) {
            selectedPositionSet?.add(position)
        }
    }

    class PickShelfViewHolder(val binding: ItemPickModifyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Picks, itemClickListener: ItemClickListener<Picks>) {
            binding.data = data
            binding.root.setOnClickListener {
                itemClickListener.onClick(absoluteAdapterPosition, data)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            ItemDiffCallback<Picks>(
                onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
