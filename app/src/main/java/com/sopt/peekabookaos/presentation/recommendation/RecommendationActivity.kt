package com.sopt.peekabookaos.presentation.recommendation

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.FriendUser
import com.sopt.peekabookaos.databinding.ActivityRecommendationBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.dialog.WarningType
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationActivity :
    BindingActivity<ActivityRecommendationBinding>(R.layout.activity_recommendation) {
    private val recommendationViewModel: RecommedationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = recommendationViewModel
        initCloseBtnOnClickListener()
        initCheckBtnOnClickListener()
        initView()
    }

    private fun initView() {
        recommendationViewModel.initRecommendData(
            // 추후 intent값 넘어올시 주석 제거
//            intent.getParcelable(BOOK_INFO, Book::class.java)!!,
//            intent.getParcelable(FRIEND_INFO, FriendUser::class.java)!!
            bookDummy,
            friendDummy
        )
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnRecommendationClose.setOnClickListener {
            finish()
        }
    }

    private fun initCheckBtnOnClickListener() {
        binding.btnRecommendationCheck.setOnClickListener {
            WarningDialogFragment().withArgs {
                /** 추후 friendDummy.name에 intent에서 넘어온 name 넣어주기 */
                putString(WarningDialogFragment.FOLLOWER, friendDummy.name)
                putSerializable(
                    WarningDialogFragment.WARNING_TYPE,
                    WarningType.WARNING_RECOMMEND
                )
                putParcelable(
                    WarningDialogFragment.CONFIRM_ACTION,
                    /** 하정아 "추천하기" 버튼 눌렀을 때 recommendationViewModel.post() 호출했당 확인하고 주석 지워 ~ */
                    ConfirmClickListener(confirmAction = { recommendationViewModel.post() })
                )
            }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
        }
    }

    companion object {
        const val BOOK_INFO = "book_info"
        const val FRIEND_INFO = "friend_info"

        private val bookDummy = Book(
            bookImage = "http://image.yes24.com/goods/90365124/XL",
            bookTitle = "아무튼, 여름",
            author = "김신회"
        )
        private val friendDummy = FriendUser(
            name = "문새연",
            profile = "",
            comment = ""
        )
    }
}
