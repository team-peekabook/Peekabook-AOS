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
        initDetailview()
        initBookIdAppearance()
    }

    private fun initBookIdAppearance() {
        detailViewModel.initBookIdData(
            intent.getIntExtra("BOOK_ID", -1)
        )
    }

    private fun initContentAppearance() {
        detailViewModel.bookData.observe(this) {
            if (it.description?.isEmpty() == true) {
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
            if (it.memo?.isEmpty() == true) {
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

    private fun initDetailview() {
        when (LOCATION) {
            MY -> {
                detailViewModel.initIsMyDetailView(true)
                detailViewModel.initDetailMyData()
            }
            FRIEND -> {
                detailViewModel.initIsMyDetailView(false)
                detailViewModel.initDetailFriendData()
            }
        }
    }

    companion object {
        const val MY = "my"
        const val FRIEND = "friend"
        private const val BOOK_ID = -1
    }
}
