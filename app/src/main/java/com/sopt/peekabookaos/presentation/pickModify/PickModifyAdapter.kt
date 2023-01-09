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
//    private var selectedPosition = RecyclerView.NO_POSITION
//    private var unSelectedPosition = RecyclerView.NO_POSITION

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

//    fun initSelectedPositionSet(hashSet: LinkedHashSet<Int>?) { // hashSet 업데이트
//        Timber.tag("kang").i("initSelectedPositionSet")
//        selectedPositionSet = hashSet
//    }

    fun updateSelectedPosition(position: Int, state: Boolean) {
        if (state) { // 선택한 상황
            Timber.tag("kang").i("updateSelectedPosition-selec: $position : position")
//            selectedPosition = position
            selectedPositionSet?.add(position)

            Timber.tag("kang").i("updateSelectedPosition-add$selectedPositionSet")
//            unSelectedPosition = RecyclerView.NO_POSITION
            for (pos in selectedPositionSet!!) {
                notifyItemChanged(pos)
                Timber.tag("kang").i("notify $pos")
            }
        } else { // 해제한 상황
//            unSelectedPosition = position
            selectedPositionSet?.remove(position)
            notifyItemChanged(position)
            Timber.tag("kang").i("updateSelectedPosition-remove$selectedPositionSet")
            for (pos in selectedPositionSet!!) {
                notifyItemChanged(pos)
                Timber.tag("kang").i("notify $pos")
            }
        }
    }

    fun updateUnSelectedPosition(position: Int, i: Int) {
        Timber.tag("kang")
            .e("remove $position: p $i: i")
//        if (selectedPositionSet.contains(position)) {
//            when (i) {
//                1 -> {
//                    firstSelectedPosition = secondSelectedPosition
//                    secondSelectedPosition = thirdSelectedPosition
//                    thirdSelectedPosition = RecyclerView.NO_POSITION
//                    Timber.tag("kang")
//                        .e("updateUnSelectedPosition: 1 " + firstSelectedPosition + ": f " + secondSelectedPosition + " : s " + thirdSelectedPosition + " : t")
//                }
//                2 -> {
//                    secondSelectedPosition = thirdSelectedPosition
//                    thirdSelectedPosition = RecyclerView.NO_POSITION
//                    Timber.tag("kang")
//                        .e("updateUnSelectedPosition: 1 " + firstSelectedPosition + ": f " + secondSelectedPosition + " : s " + thirdSelectedPosition + " : t")
//                }
//                3 -> {
//                    thirdSelectedPosition = RecyclerView.NO_POSITION
//                    Timber.tag("kang")
//                        .e("updateUnSelectedPosition: 1 " + firstSelectedPosition + ": f " + secondSelectedPosition + " : s " + thirdSelectedPosition + " : t")
//                }
//            }
//            selectedPositionSet.remove(position)
//            Timber.tag("kang")
//                .e("$selectedPositionSet : selectedPositionSet")
//        unSelectedPosition = position
//        selectedPosition = RecyclerView.NO_POSITION
//            notifyItemChanged(firstSelectedPosition)
//            notifyItemChanged(secondSelectedPosition)
//            notifyItemChanged(thirdSelectedPosition)
//            notifyItemChanged(position)
//        notifyItemChanged(selectedPosition)
//        notifyItemChanged(unSelectedPosition)
//        }
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
