package com.sopt.peekabookaos.presentation.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityDetailBinding
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.BOOK
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.BOOK_COMMENT
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.UPDATE
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.dialog.WarningType
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = detailViewModel
        initBookIdAppearance()
        initContentAppearance()
        initDetailView()
        initBookIdObserve()
        initDeleteBtnClickListener()
        initBackBtnOnClickListener()
        initEditBtnClickListener()
    }

    private fun initBookIdAppearance() {
        detailViewModel.initBookId(
            intent.getIntExtra(BOOK_INFO, DEFAULT)
        )
    }

    private fun initBookIdObserve() {
        detailViewModel.bookId.observe(this) { bookId ->
            detailViewModel.getDetail(bookId)
        }
    }

    private fun initContentAppearance() {
        detailViewModel.bookComment.observe(this) {
            if (it.description.isEmpty()) {
                with(binding) {
                    tvDetailGetContent.text = getString(R.string.text_detail_description_is_null)
                    tvDetailGetContent.setTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.peeka_g_1
                        )
                    )
                }
            }
            if (it.memo.isEmpty()) {
                with(binding) {
                    tvDetailGetMemo.text = getString(R.string.text_detail_memo_is_null)
                    tvDetailGetMemo.setTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.peeka_g_1
                        )
                    )
                }
            }
        }
    }

    private fun initDetailView() {
        when (intent.getStringExtra(LOCATION)) {
            MY -> {
                detailViewModel.initIsMyDetailView(true)
                intent.getIntExtra(BOOK_INFO, DEFAULT)
            }
            FRIEND -> {
                detailViewModel.initIsMyDetailView(false)
                intent.getIntExtra(BOOK_INFO, DEFAULT)
            }
        }
    }

    private fun initBackBtnOnClickListener() {
        binding.btnDetailBack.setSingleOnClickListener {
            finish()
        }
    }

    private fun initEditBtnClickListener() {
        binding.btnDetailEdit.setSingleOnClickListener {
            Intent(this, CreateUpdateBookActivity::class.java).apply {
                putExtra(LOCATION, UPDATE)
                putExtra(BOOK, detailViewModel.bookData.value)
                putExtra(BOOK_COMMENT, detailViewModel.bookComment.value)
            }.also { intent ->
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initDeleteBtnClickListener() {
        binding.btnDetailDelete.setSingleOnClickListener {
            WarningDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(
                        WarningDialogFragment.WARNING_TYPE,
                        WarningType.WARNING_DELETE_BOOK
                    )
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        ConfirmClickListener(confirmAction = { detailViewModel.deleteDetail() })
                    )
                }
            }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
            initIsDeletedObserve()
        }
    }

    private fun initIsDeletedObserve() {
        detailViewModel.isDeleted.observe(this) { success ->
            if (success) {
                finish()
            }
        }
    }

    companion object {
        const val MY = "my"
        const val FRIEND = "friend"
        const val BOOK_INFO = "book_info"
        private const val DEFAULT = 3 // 추후 -1로 수정
    }
}
