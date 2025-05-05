package com.sopt.peekabookaos.presentation.notification

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentNotificationRecommendedBinding
import com.sopt.peekabookaos.presentation.recommend.RecommendAdapter
import com.sopt.peekabookaos.presentation.recommend.RecommendViewModel
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationRecommendedFragment :
    BindingFragment<FragmentNotificationRecommendedBinding>(R.layout.fragment_notification_recommended) {
    private val recommendViewModel: RecommendViewModel by viewModels()
    private val recommendAdapter: RecommendAdapter?
        get() = binding.rvNotificationRecommended.adapter as? RecommendAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = recommendViewModel
        recommendViewModel.getRecommend()
        initAdapter()
        initObserver()
        initBackClickListener()
    }

    private fun initAdapter() {
        binding.rvNotificationRecommended.adapter = RecommendAdapter(::onClickDelete)
    }

    private fun onClickDelete(recommendId: Int) {
        // 불필요한 코드 (RecommendedFragment 쇽샥 이슈)
    }

    private fun initObserver() {
        recommendViewModel.recommendedBook.observe(viewLifecycleOwner) { books ->
            if (books.isEmpty()) {
                binding.tvNotificationRecommendedEmpty.isVisible = true
                binding.rvNotificationRecommended.isVisible = false
                binding.tvNotificationRecommendedEmpty.text =
                    getString(R.string.recommend_recommended_empty)
            } else {
                binding.tvNotificationRecommendedEmpty.isVisible = false
                binding.rvNotificationRecommended.isVisible = true
                recommendAdapter?.submitList(books)
            }
        }
    }

    private fun initBackClickListener() {
        binding.ivNotificationRecommendedBack.setSingleOnClickListener {
            findNavController().popBackStack()
        }
    }
}
