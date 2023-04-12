package com.sopt.peekabookaos.presentation.block

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityBlockBinding
import com.sopt.peekabookaos.presentation.block.UnblockDialog.Companion.BLOCK_INDEX
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockActivity : BindingActivity<ActivityBlockBinding>(R.layout.activity_block) {
    private val blockViewModel: BlockViewModel by viewModels()
    private val blockAdapter = BlockAdapter(showUnblockDialog = ::initUnblockDialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = blockViewModel
        initAdapter()
        initBackBtnOnClickListener()
        initIsDeletedObserve()
    }

    private fun initAdapter() {
        binding.rvBlock.adapter = blockAdapter
        blockViewModel.blockUser.observe(this) { blockData ->
            blockAdapter.submitList(blockData)
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
        blockViewModel.isDeleted.observe(this) { success ->
            if (success) {
                blockViewModel.getBlockList()
            }
        }
    }
}
