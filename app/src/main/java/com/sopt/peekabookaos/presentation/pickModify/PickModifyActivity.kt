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
        binding.vm = viewModel
        initAdapter()
        initItemDecoration()
    }

    private fun initAdapter() {
        pickShelfAdapter = PickModifyAdapter { _, _ ->
//            viewModel.updateSelectedItem(pos, item)
//            var index = viewModel.getSelectedItemIndex(pos, item)
//            if (viewModel.itemSelectState.value == true) {
//                Timber.tag("kang").d("activity- 추가 포지션 update $pos 포지션")
//                pickShelfAdapter.updateSelectedPosition( // 누른 포지션 update
//                    viewModel.position, // 인자: 아이템 포지션이랑 인덱스
//                    index
//                )
//            } else {
//                Timber.tag("kang").d("activity- 삭제 포지션 update $pos 포지션")
//                pickShelfAdapter.updateUnSelectedPosition( // 삭제한 포지션 update
//                    viewModel.position, // 인자: 아이템 포지션이랑 인덱스
//                    index
//                )
        }
        binding.rvPickModify.adapter = pickShelfAdapter
        pickShelfAdapter.submitList(viewModel.pickModifyData.value)
    }

    private fun initItemDecoration() {
        itemDeco = PickModifyDecoration(this)
        binding.rvPickModify.addItemDecoration(itemDeco)
    }
}
