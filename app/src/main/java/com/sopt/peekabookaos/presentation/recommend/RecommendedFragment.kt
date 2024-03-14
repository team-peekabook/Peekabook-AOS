package com.sopt.peekabookaos.presentation.recommend

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ItemRecommendViewBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendedFragment :
    BindingFragment<ItemRecommendViewBinding>(R.layout.item_recommend_view) {
    private val recommendViewModel by activityViewModels<RecommendViewModel>()
    private val recommendAdapter: RecommendAdapter?
        get() = binding.rvRecommend.adapter as? RecommendAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = recommendViewModel
        initAdapter()
        initObserver()
    }

    private fun initAdapter() {
        binding.rvRecommend.adapter = RecommendAdapter(::onClickDelete)
    }

    private fun onClickDelete(recommendId: Int) {
        recommendViewModel.setRecommendId(recommendId)
        RecommendDeleteDialog().withArgs {
            putSerializable(
                RecommendDeleteDialog.RECOMMEND_TYPE,
                RecommendType.RECOMMENDED
            )
        }.show(childFragmentManager, RecommendDeleteDialog.DIALOG_TYPE)
    }

    private fun initObserver() {
        recommendViewModel.recommendedBook.observe(viewLifecycleOwner) { books ->
            if (books.isEmpty()) {
                binding.tvRecommendEmpty.isVisible = true
                binding.rvRecommend.isVisible = false
                binding.tvRecommendEmpty.text = getString(R.string.recommend_recommended_empty)
            } else {
                binding.tvRecommendEmpty.isVisible = false
                binding.rvRecommend.isVisible = true
                recommendAdapter?.submitList(books)
            }
        }
    }
}
