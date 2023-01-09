package com.sopt.peekabookaos.presentation.pickModify

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityPickModifyBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickModifyActivity :
    BindingActivity<ActivityPickModifyBinding>(R.layout.activity_pick_modify) {
    private lateinit var itemDeco: PickModifyDecoration
    private lateinit var pickShelfAdapter: PickModifyAdapter
    private val viewModel by viewModels<PickModifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initAdapter()
        initItemDecoration()
    }

    private fun initAdapter() {
        pickShelfAdapter = PickModifyAdapter { position, item ->
            viewModel.updateSelectedItemState(item)
            viewModel.selectState.value?.let {
                pickShelfAdapter.updateSelectedPosition(
                    position,
                    it
                )
            }
        }
        binding.rvPickModify.adapter = pickShelfAdapter
        pickShelfAdapter.submitList(viewModel.pickModifyData.value)

        val animator = binding.rvPickModify.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }

    private fun initItemDecoration() {
        itemDeco = PickModifyDecoration(this)
        binding.rvPickModify.addItemDecoration(itemDeco)
    }
}
