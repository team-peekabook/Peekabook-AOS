package com.sopt.peekabookaos.presentation.block

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityBlockBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockActivity : BindingActivity<ActivityBlockBinding>(R.layout.activity_block) {
    private val blockViewModel: BlockViewModel by viewModels()
    private val blockAdapter: FriendBlockAdapter?
        get() = binding.rvBlock.adapter as? FriendBlockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = blockViewModel
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvBlock.adapter = FriendBlockAdapter()
        blockAdapter?.submitList(blockViewModel.blockList)
    }
}
