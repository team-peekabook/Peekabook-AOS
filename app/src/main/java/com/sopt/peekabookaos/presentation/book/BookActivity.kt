package com.sopt.peekabookaos.presentation.book

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityBookBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookActivity : BindingActivity<ActivityBookBinding>(R.layout.activity_book) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSearchFragmentView()
    }

    private fun initSearchFragmentView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_book) as NavHostFragment
        val navController = navHostFragment.navController

        when (intent.getStringExtra(LOCATION) ?: CREATE) {
            RECOMMEND -> {
                navController.navigate(R.id.action_barcodeScannerFragment_to_searchBookFragment)
            }
            else -> {
                navController.navigate(R.id.barcodeScannerFragment)
            }
        }
    }

    companion object {
        const val UPDATE = "update"
        const val BOOK_COMMENT = "book_comment"
        const val RECOMMEND = "recommend"
        const val CREATE = "create"
        const val BOOK = "book"
        const val LOCATION = "location"
        const val BOOK_INFO = "book_info"
        const val FRIEND_INFO = "friend_info"
    }
}
