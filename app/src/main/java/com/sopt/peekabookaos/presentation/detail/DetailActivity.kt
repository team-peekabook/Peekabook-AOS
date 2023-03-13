package com.sopt.peekabookaos.presentation.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityDetailBinding
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_COMMENT
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_ID
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.UPDATE
import com.sopt.peekabookaos.presentation.editBook.EditBookActivity
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.dialog.WarningType
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        detailViewModel.initBookId(intent.getIntExtra(BOOK_ID, DEFAULT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = detailViewModel
        initDetailView()
        initContentAppearance()
        initBookIdObserve()
        initDeleteBtnClickListener()
        initBackBtnOnClickListener()
        initEditBtnClickListener()
    }

    private fun initBookIdObserve() {
        detailViewModel.bookId.observe(this) {
            detailViewModel.getDetail()
        }
    }

    private fun initContentAppearance() {
        detailViewModel.bookComment.observe(this) { bookComment ->
            if (bookComment.description.isNullOrBlank()) {
                with(binding) {
                    tvDetailGetContent.text = getString(R.string.detail_description_empty)
                    tvDetailGetContent.setTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.peeka_g_1
                        )
                    )
                }
            } else {
                with(binding) {
                    tvDetailGetContent.text = bookComment.description
                    tvDetailGetContent.setTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.peeka_red
                        )
                    )
                }
            }

            if (bookComment.memo.isNullOrBlank()) {
                with(binding) {
                    tvDetailGetMemo.text = getString(R.string.detail_memo_empty)
                    tvDetailGetMemo.setTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.peeka_g_1
                        )
                    )
                }
            } else {
                with(binding) {
                    tvDetailGetMemo.text = bookComment.memo
                    tvDetailGetMemo.setTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.peeka_red
                        )
                    )
                }
            }
        }
    }

    private fun initDetailView() {
        when (intent.getStringExtra(LOCATION)) {
            MY_SHELF -> {
                detailViewModel.initIsMyDetailView(true)
                detailViewModel.initBookId(
                    intent.getIntExtra(BOOK_ID, DEFAULT)
                )
            }
            FRIEND_SHELF -> {
                detailViewModel.initIsMyDetailView(false)
                detailViewModel.initBookId(
                    intent.getIntExtra(BOOK_ID, DEFAULT)
                )
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
            detailViewModel.updateBookData()
            Intent(this, EditBookActivity::class.java).apply {
                putExtra(LOCATION, UPDATE)
                putExtra(BOOK_INFO, detailViewModel.bookData.value)
                putExtra(BOOK_COMMENT, detailViewModel.bookComment.value)
            }.also { intent ->
                startActivity(intent)
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
        const val MY_SHELF = "my"
        const val FRIEND_SHELF = "friend"
        const val DEFAULT = -1
    }
}
