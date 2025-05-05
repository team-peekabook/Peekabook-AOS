package com.sopt.peekabookaos.presentation.recommendation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentRecommendationBinding
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.presentation.book.BookActivity
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.dialog.WarningType
import com.sopt.peekabookaos.util.extensions.getParcelableCompat
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationFragment :
    BindingFragment<FragmentRecommendationBinding>(R.layout.fragment_recommendation) {
    private val recommendationViewModel: RecommedationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = recommendationViewModel
        initView()
        initEditTextClearFocus()
        initIsRecommendationObserve()
        initBackBtnOnClickListener()
        initCheckBtnOnClickListener()
        initIsRecommendationObserve()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clRecommendation.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }

        binding.btnRecommendationCheck.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }
    }

    private fun initView() {
        recommendationViewModel.initRecommendData(
            requireNotNull(
                arguments?.getParcelableCompat(BookActivity.BOOK_INFO) ?: Book()
            ),
            requireNotNull(
                arguments?.getParcelableCompat(BookActivity.FRIEND_INFO) ?: User()
            )
        )
    }

    private fun initBackBtnOnClickListener() {
        binding.btnRecommendationBack.setOnClickListener {
            findNavController().popBackStack()
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
            }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
        }
    }

    private fun initIsRecommendationObserve() {
        recommendationViewModel.isRecommendation.observe(viewLifecycleOwner) { success ->
            if (success) {
                moveToMain()
            }
        }
    }

    private fun moveToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }
}
