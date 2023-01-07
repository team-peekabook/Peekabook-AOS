package com.sopt.peekabookaos.presentation.pickModify

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
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
        pickShelfAdapter = PickModifyAdapter { pos, item ->
            viewModel.updateSelectedItem(pos, item)
            if (viewModel.itemSelectState.value == true) {
                pickShelfAdapter.updateSelectedPosition(viewModel.position)
                Log.e("kang", "ac: ${viewModel.position}")
            } else {
                pickShelfAdapter.updateUnSelectedPosition(viewModel.position)
                Log.e("kang", "ac: ${viewModel.position}")
            }
        }
        binding.rvPickModify.adapter = pickShelfAdapter
        pickShelfAdapter.submitList(viewModel.pickShelfData.value)
    }

    private fun initItemDecoration() {
        itemDeco = PickModifyDecoration(this)
        binding.rvPickModify.addItemDecoration(itemDeco)
    }
}
