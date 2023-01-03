package com.sopt.peekabookaos.presentation.createUpdateBook

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.CreateUpdateBook
import com.sopt.peekabookaos.databinding.ActivityCreateUpdateBookBinding
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
    }

    private fun initView() {
        when (intent.getStringExtra(LOCATION)) {
            UPDATE -> {
                createUpdateBookViewModel.initIsUpdateView(true)
                createUpdateBookViewModel.initCreateUpdateBookData(
                    intent.getParcelable(UPDATE, CreateUpdateBook::class.java)!!
                )
            }
            else -> {
                createUpdateBookViewModel.initIsUpdateView(false)
                /* 추후 bookData 대신 intent.getParcelable(CREATE, CreateUpdateBook::class.java)!! */
                createUpdateBookViewModel.initCreateUpdateBookData(bookData)
            }
        }
    }

    private fun initCloseBtnOnClickListener() {
        binding.btnCreateUpdateBookClose.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val LOCATION = "location"
        const val UPDATE = "update"
        const val CREATE = "create"

        /* 추후 제거 예정*/
        private val bookData = CreateUpdateBook(
            bookImage = "http://image.yes24.com/goods/90365124/XL",
            bookTitle = "아무튼, 여름",
            author = "김신회",
            description = "",
            memo = "asdf"
        )
    }
}
