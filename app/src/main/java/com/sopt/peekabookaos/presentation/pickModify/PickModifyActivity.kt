package com.sopt.peekabookaos.presentation.pickModify

import android.os.Bundle
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
        initItemDecoration()
        initAdapter()
    }

    private fun initAdapter() {
        pickShelfAdapter = PickModifyAdapter()
        binding.rvPickModify.adapter = pickShelfAdapter
        pickShelfAdapter.submitList(viewModel.pickShelfData.value)
    }

    private fun initItemDecoration() {
        itemDeco = PickModifyDecoration(this)
        binding.rvPickModify.addItemDecoration(itemDeco)
    }
}