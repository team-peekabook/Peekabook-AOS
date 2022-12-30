package com.sopt.peekabookaos.presentation.recommend

import android.os.Bundle
import android.view.View
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentRecommendBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendFragment : BindingFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
