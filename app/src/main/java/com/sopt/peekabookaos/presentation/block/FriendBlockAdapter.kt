package com.sopt.peekabookaos.presentation.block

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemBlockBinding
import com.sopt.peekabookaos.domain.entity.FriendList
import com.sopt.peekabookaos.util.ItemDiffCallback
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener

class FriendBlockAdapter(private val showBlockDialog: (FriendList, Int) -> Unit) :
    ListAdapter<FriendList, FriendBlockAdapter.FriendBlockViewHolder>(blockDiffUtil) {
    class FriendBlockViewHolder(
        private val binding: ItemBlockBinding,
        private val showBlockDialog: (FriendList, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(blockFriend: FriendList) {
            binding.data = blockFriend
            binding.tvBlockCancel.setSingleOnClickListener {
                showBlockDialog(blockFriend, blockFriend.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendBlockViewHolder =
        FriendBlockViewHolder(
            ItemBlockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            showBlockDialog
        )

    override fun onBindViewHolder(holder: FriendBlockViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val blockDiffUtil = ItemDiffCallback<FriendList>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
