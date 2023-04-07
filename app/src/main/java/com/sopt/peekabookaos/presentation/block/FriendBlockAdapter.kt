package com.sopt.peekabookaos.presentation.block

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemBlockBinding
import com.sopt.peekabookaos.domain.entity.FriendList
import com.sopt.peekabookaos.util.ItemDiffCallback

class FriendBlockAdapter(private val showBlockCancelDialog: () -> Unit) :
    ListAdapter<FriendList, FriendBlockAdapter.FriendBlockViewHolder>(blockDiffUtil) {
    class FriendBlockViewHolder(
        private val binding: ItemBlockBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(blockFriend: FriendList, showBlockCancelDialog: () -> Unit) {
            binding.data = blockFriend
            binding.tvBlockCancel.setOnClickListener {
                showBlockCancelDialog()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendBlockViewHolder {
        val binding = ItemBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendBlockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendBlockViewHolder, position: Int) {
        holder.onBind(getItem(position), showBlockCancelDialog)
    }

    companion object {
        private val blockDiffUtil = ItemDiffCallback<FriendList>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
