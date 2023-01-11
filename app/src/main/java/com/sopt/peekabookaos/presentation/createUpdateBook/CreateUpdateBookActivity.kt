package com.sopt.peekabookaos.presentation.createUpdateBook

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.databinding.ActivityCreateUpdateBookBinding
import com.sopt.peekabookaos.presentation.detail.DetailActivity
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.getParcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUpdateBookActivity :
    BindingActivity<ActivityCreateUpdateBookBinding>(R.layout.activity_create_update_book) {
    private val createUpdateBookViewModel: CreateUpdateBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = createUpdateBookViewModel
        initView()
        initCloseBtnOnClickListener()
        initIsServerStatus()
    }

    private fun initView() {
        when (intent.getStringExtra(LOCATION)) {
            UPDATE -> {
                createUpdateBookViewModel.initIsUpdateView(true)
                createUpdateBookViewModel.initCreateUpdateBookData(
                    intent.getParcelable(UPDATE, Book::class.java)!!
                )
            }
            else -> {
                createUpdateBookViewModel.initIsUpdateView(false)
                createUpdateBookViewModel.initCreateUpdateBookData(
                    intent.getParcelable(CREATE, Book::class.java)!!
                )
                // 수정뷰에서 넘어올 경우 comment, memo도 전달받기
            }
        }
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnCreateUpdateBookClose.setOnClickListener {
            finish()
        }
    }

    private fun initIsServerStatus() {
        createUpdateBookViewModel.isServerStatus.observe(this) { success ->
            if (success) {
                val toDetail = Intent(this@CreateUpdateBookActivity, DetailActivity::class.java)
                startActivity(toDetail)
                finish()
            }
        }
    }

    companion object {
        const val LOCATION = "location"
        const val UPDATE = "update"
        const val CREATE = "create"
        const val BOOK = "book"
        const val BOOK_COMMENT = "book_comment"
    }
}
