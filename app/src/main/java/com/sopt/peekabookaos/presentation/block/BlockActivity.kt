package com.sopt.peekabookaos.presentation.block

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityBlockBinding
import com.sopt.peekabookaos.presentation.bookshelf.BlockDialog
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockActivity : BindingActivity<ActivityBlockBinding>(R.layout.activity_block) {
    private val blockViewModel: BlockViewModel by viewModels()
    private val blockAdapter: FriendBlockAdapter?
        get() = binding.rvBlock.adapter as? FriendBlockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = blockViewModel

        blockAdapter?.setItemClickListener(object : FriendBlockAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                initBlockDialog()
            }
        })
        initAdapter()
        initBackBtnOnClickListener()
    }

    private fun initAdapter() {
        binding.rvBlock.adapter = FriendBlockAdapter()
        blockViewModel.isServerStatus.observe(this) { success ->
            if (success) {
                blockAdapter?.submitList(blockViewModel.blockData.value)
            }
        }
    }

    private fun initBackBtnOnClickListener() {
        binding.btnBlockBack.setSingleOnClickListener {
            finish()
        }
    }

    private fun initBlockDialog() {
        BlockDialog().withArgs {
            putString(
                BlockDialog.FOLLOWER,
                requireNotNull(blockViewModel.friendData.value).nickname
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = { blockViewModel.deleteBlock() })
            )
        }.show(supportFragmentManager, BlockDialog.TAG)
    }
}
