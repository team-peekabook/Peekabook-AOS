package com.sopt.peekabookaos.presentation.recommendation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityRecommendationBinding
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.SelfIntro
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.FRIEND_INFO
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.dialog.WarningType
import com.sopt.peekabookaos.util.extensions.KeyBoardUtil
import com.sopt.peekabookaos.util.extensions.getParcelable
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationActivity :
    BindingActivity<ActivityRecommendationBinding>(R.layout.activity_recommendation) {
    private val recommendationViewModel: RecommedationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = recommendationViewModel
        initView()
        initEditTextClearFocus()
        initIsRecommendationObserve()
        initCloseBtnOnClickListener()
        initCheckBtnOnClickListener()
        initIsRecommendationObserve()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clRecommendation.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }

        binding.btnRecommendationCheck.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }
    }

    private fun initView() {
        recommendationViewModel.initRecommendData(
            intent.getParcelable(BOOK_INFO, Book::class.java)!!,
            intent.getParcelable(FRIEND_INFO, SelfIntro::class.java)!!
        )
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnRecommendationClose.setSingleOnClickListener {
            finish()
        }
    }

    private fun initCheckBtnOnClickListener() {
        binding.btnRecommendationCheck.setSingleOnClickListener {
            WarningDialogFragment().withArgs {
                putString(
                    WarningDialogFragment.FOLLOWER,
                    recommendationViewModel.friendData.value!!.nickname
                )
                putSerializable(
                    WarningDialogFragment.WARNING_TYPE,
                    WarningType.WARNING_RECOMMEND
                )
                putParcelable(
                    WarningDialogFragment.CONFIRM_ACTION,
                    ConfirmClickListener(confirmAction = { recommendationViewModel.postRecommendation() })
                )
            }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
        }
    }

    private fun initIsRecommendationObserve() {
        recommendationViewModel.isRecommendation.observe(this) { success ->
            if (success) {
                finish()
            }
        }
    }
}
