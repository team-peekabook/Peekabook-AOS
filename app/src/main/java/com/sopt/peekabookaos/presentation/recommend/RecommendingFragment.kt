package com.sopt.peekabookaos.presentation.recommend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ItemRecommendViewBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendingFragment :
    BindingFragment<ItemRecommendViewBinding>(R.layout.item_recommend_view) {
    private val recommendViewModel by activityViewModels<RecommendViewModel>()
    private val recommendAdapter: BookRecommendAdapter?
        get() = binding.rvRecommend.adapter as? BookRecommendAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = recommendViewModel
        initAdapter()
        initObserver()
    }

    private fun initAdapter() {
        binding.rvRecommend.adapter = BookRecommendAdapter()
    }

    private fun initObserver() {
        recommendViewModel.recommendingBook.observe(viewLifecycleOwner) { books ->
            recommendAdapter?.submitList(books)
        }
    }
}
