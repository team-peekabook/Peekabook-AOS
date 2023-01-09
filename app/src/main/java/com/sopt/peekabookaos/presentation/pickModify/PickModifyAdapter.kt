package com.sopt.peekabookaos.presentation.pickModify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.PickModify
import com.sopt.peekabookaos.databinding.ItemPickModifyBinding
import com.sopt.peekabookaos.presentation.bookshelf.ItemClickListener
import com.sopt.peekabookaos.util.extensions.ItemDiffCallback
import timber.log.Timber

class PickModifyAdapter(
    private val clickListener: ItemClickListener<PickModify>
) :
    androidx.recyclerview.widget.ListAdapter<PickModify, PickModifyAdapter.PickShelfViewHolder>(
        DIFF_CALLBACK
    ) {
    private var selectedPositionSet: LinkedHashSet<Int>? = linkedSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickShelfViewHolder {
        val itemPickModifyBinding =
            ItemPickModifyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PickShelfViewHolder(itemPickModifyBinding)
    }

    override fun onBindViewHolder(holder: PickShelfViewHolder, position: Int) {
        holder.onBind(getItem(position), clickListener)
        holder.binding.ivItemBookshelfShelfSelect.isVisible =
            (selectedPositionSet?.contains(position) == true && position != RecyclerView.NO_POSITION)
        holder.binding.tvItemBookshelfShelfPick.isVisible =
            (selectedPositionSet?.contains(position) == true && position != RecyclerView.NO_POSITION)
    }

    fun updateSelectedPosition(position: Int, state: Boolean) {
        if (state) { // 선택한 상황
            Timber.tag("kang").i("updateSelectedPosition-selec: $position : position")
            selectedPositionSet?.add(position)

            Timber.tag("kang").i("updateSelectedPosition-add$selectedPositionSet")
            for (pos in selectedPositionSet!!) {
                notifyItemChanged(pos)
                Timber.tag("kang").i("notify $pos")
            }
        } else { // 해제한 상황
            selectedPositionSet?.remove(position)
            notifyItemChanged(position)
            Timber.tag("kang").i("updateSelectedPosition-remove$selectedPositionSet")
            for (pos in selectedPositionSet!!) {
                notifyItemChanged(pos)
                Timber.tag("kang").i("notify $pos")
            }
        }
    }

    class PickShelfViewHolder(val binding: ItemPickModifyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PickModify, itemClickListener: ItemClickListener<PickModify>) {
            binding.data = data
            binding.root.setOnClickListener {
                itemClickListener.onClick(absoluteAdapterPosition, data)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            ItemDiffCallback<PickModify>(
                onItemsTheSame = { old, new -> old.book == new.book },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
