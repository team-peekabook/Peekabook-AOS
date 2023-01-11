package com.sopt.peekabookaos.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityDetailBinding
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.LOCATION
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = detailViewModel
        initContentAppearance()
        initDetailView()
        initBookIdAppearance()
        initBookIdObserve()
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
        detailViewModel.detailData.observe(this) {
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
        when (LOCATION) {
            MY -> {
                detailViewModel.initIsMyDetailView(true)
            }
            FRIEND -> {
                detailViewModel.initIsMyDetailView(false)
            }
        }
    }

    companion object {
        const val MY = "my"
        const val FRIEND = "friend"
        const val BOOK_INFO = "book_info"
        private const val DEFAULT = 2 // 추후 -1로 수정
    }
}
