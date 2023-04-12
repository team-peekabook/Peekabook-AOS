package com.sopt.peekabookaos.presentation.block

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemBlockBinding
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.util.ItemDiffCallback
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener

class BlockedUserAdapter(private val showUnblockDialog: (Int) -> Unit) :
    ListAdapter<User, BlockedUserAdapter.BlockedUserViewHolder>(unblockDiffUtil) {
    class BlockedUserViewHolder(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockedUserViewHolder =
        BlockedUserViewHolder(
            ItemBlockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            showUnblockDialog
        )

    override fun onBindViewHolder(holder: BlockedUserViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val unblockDiffUtil = ItemDiffCallback<User>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
