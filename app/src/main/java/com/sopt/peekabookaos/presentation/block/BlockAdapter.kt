package com.sopt.peekabookaos.presentation.block

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemBlockBinding
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.util.ItemDiffCallback
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener

class BlockAdapter(private val showUnblockDialog: (Int) -> Unit) :
    ListAdapter<User, BlockAdapter.FriendBlockViewHolder>(unblockDiffUtil) {
    class FriendBlockViewHolder(
        private val binding: ItemBlockBinding,
        private val showUnblockDialog: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            binding.data = user
            binding.tvBlockUnblock.setSingleOnClickListener {
                showUnblockDialog(absoluteAdapterPosition)
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
            showUnblockDialog
        )

    override fun onBindViewHolder(holder: FriendBlockViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val unblockDiffUtil = ItemDiffCallback<User>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
