package com.sopt.peekabookaos.presentation.block

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityBlockedUserBinding
import com.sopt.peekabookaos.presentation.block.UnblockDialog.Companion.BLOCK_INDEX
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockedUserActivity :
    BindingActivity<ActivityBlockedUserBinding>(R.layout.activity_blocked_user) {
    private val blockedUserViewModel: BlockedUserViewModel by viewModels()
    private val blockedUserAdapter = BlockedUserAdapter(showUnblockDialog = ::initUnblockDialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = blockedUserViewModel
        initAdapter()
        initBackBtnOnClickListener()
        initIsDeletedObserve()
    }

    private fun initBlockedUserEmpty() {
        with(binding) {
            if (requireNotNull(blockedUserViewModel.blockUser.value).isEmpty()) {
                tvBlockedUserEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun initAdapter() {
        binding.rvBlock.adapter = blockedUserAdapter
        blockedUserViewModel.blockUser.observe(this) { blockData ->
            blockedUserAdapter.submitList(blockData)
            initBlockedUserEmpty()
        }
    }

    private fun initBackBtnOnClickListener() {
        binding.btnBlockBack.setSingleOnClickListener {
            finish()
        }
    }

    private fun initUnblockDialog(index: Int) {
        UnblockDialog().withArgs { putInt(BLOCK_INDEX, index) }
            .show(supportFragmentManager, UnblockDialog.TAG)
    }

    private fun initIsDeletedObserve() {
        blockedUserViewModel.isDeleted.observe(this) { success ->
            if (success) {
                blockedUserViewModel.getBlockList()
            }
        }
    }
}
