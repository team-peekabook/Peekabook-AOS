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
    private val recommendationViewModel: RecommedationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = recommendationViewModel
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
/*
    companion object {
        const val BOOK_INFO = "book_info"
        const val FRIEND_INFO = "friend_info"
    }
 */
}
