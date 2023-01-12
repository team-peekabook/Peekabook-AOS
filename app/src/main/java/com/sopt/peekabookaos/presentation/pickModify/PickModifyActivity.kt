package com.sopt.peekabookaos.presentation.pickModify

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityPickModifyBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.ToastMessageUtil
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickModifyActivity :
    BindingActivity<ActivityPickModifyBinding>(R.layout.activity_pick_modify) {
    private lateinit var itemDeco: PickModifyDecoration
    private lateinit var pickShelfAdapter: PickModifyAdapter
    private val viewModel: PickModifyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initAdapter()
        initIsServerObserve()
        initItemDecoration()
        initCheckClickListener()
        initObserver()
    }

    private fun initIsServerObserve() {
        viewModel.isServerStatus.observe(this) { success ->
            if (success) {
                pickShelfAdapter.submitList(viewModel.pickModifyData.value)
            }
        }
    }

    private fun initAdapter() {
        pickShelfAdapter = PickModifyAdapter { position, item ->
            viewModel.updateSelectedItemState(item)
            viewModel.selectState.value?.let {
                pickShelfAdapter.updateSelectedPosition(position, it)
            }
        }
        binding.rvPickModify.adapter = pickShelfAdapter

        val animator = binding.rvPickModify.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }

    private fun initItemDecoration() {
        itemDeco = PickModifyDecoration(this)
        binding.rvPickModify.addItemDecoration(itemDeco)
    }

    private fun initCheckClickListener() {
        binding.ivPickModifyCheck.setSingleOnClickListener {
            viewModel.changeHashToPickRequest()
            viewModel.patchPick()
            finish()
        }
    }

    private fun initObserver() {
        viewModel.overListState.observe(
            this,
            Observer {
                if (viewModel.overListState.value == true) ToastMessageUtil.showToast(
                    this,
                    getString(R.string.pick_modify_notice)
                )
            }
        )
    }
}
