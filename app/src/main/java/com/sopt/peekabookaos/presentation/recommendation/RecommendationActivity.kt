package com.sopt.peekabookaos.presentation.recommendation

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityRecommendationBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationActivity :
    BindingActivity<ActivityRecommendationBinding>(R.layout.activity_recommendation) {
    private val RecommendationViewModel: RecommedationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = RecommendationViewModel
        initCloseBtnOnClickListener()
        initSaveBtnOnClickListener()
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnRecommendationClose.setOnClickListener {
            finish()
        }
    }

    private fun initSaveBtnOnClickListener() {
        binding.btnRecommendationCheck.setOnClickListener {
        }
    }
}
