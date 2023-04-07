package com.sopt.peekabookaos.presentation.block

import android.os.Bundle
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
    private val blockAdapter = FriendBlockAdapter(::initBlockDialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = blockViewModel
        initAdapter()
        initBackBtnOnClickListener()
    }

    private fun initAdapter() {
        binding.rvBlock.adapter = blockAdapter
        blockViewModel.isServerStatus.observe(this) { success ->
            if (success) {
                blockAdapter.submitList(blockViewModel.blockData.value)
            }
        }
    }

    private fun initBackBtnOnClickListener() {
        binding.btnBlockBack.setSingleOnClickListener {
            finish()
        }
    }

    private fun initBlockDialog() {
        BlockDeleteDialog().withArgs {
            putString(
                BlockDialog.FOLLOWER,
                requireNotNull(blockViewModel.friendData.value).nickname
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = { blockViewModel.deleteBlock() })
            )
        }.show(supportFragmentManager, BlockDialog.TAG)
        initIsDeletedObserve()
    }

    private fun initIsDeletedObserve() {
        blockViewModel.isDeleted.observe(this) { success ->
            if (success) {
                finish()
            }
        }
    }
}
