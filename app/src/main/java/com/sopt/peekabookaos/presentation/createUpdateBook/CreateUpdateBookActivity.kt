package com.sopt.peekabookaos.presentation.createUpdateBook

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
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
                createUpdateBookViewModel.initIsUpdate(true)
                createUpdateBookViewModel.initCreateUpdateBookData(
                    intent.getParcelable(UPDATE, CreateUpdateBook::class.java)!!
                )
            }
            else -> {
                createUpdateBookViewModel.initIsUpdate(false)
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

    }
}
