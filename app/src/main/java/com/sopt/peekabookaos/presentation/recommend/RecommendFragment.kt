package com.sopt.peekabookaos.presentation.recommend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentRecommendBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendFragment : BindingFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {
    private val recommendViewModel by activityViewModels<RecommendViewModel>()
    private lateinit var viewPagerAdapter: RecommendPagerAdapter

    override fun onResume() {
        super.onResume()
        recommendViewModel.getRecommend()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = recommendViewModel
        initAdapter()
    }

    private fun initAdapter() {
        viewPagerAdapter = RecommendPagerAdapter(requireActivity())

        binding.vpRecommend.adapter = viewPagerAdapter

        binding.vpRecommend.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        TabLayoutMediator(binding.tlRecommend, binding.vpRecommend) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.recommend_recommended)
                1 -> tab.text = getString(R.string.recommend_recommending)
            }
        }.attach()
    }
}
