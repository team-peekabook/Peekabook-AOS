package com.sopt.peekabookaos.presentation.book

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityBookBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.activityCloseTransition
import com.sopt.peekabookaos.util.extensions.activityOpenTransition
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

        navController.navigate(R.id.searchBookFragment)
        activityOpenTransition(R.anim.anim_from_right, R.anim.anim_to_left)
    }

    override fun finish() {
        super.finish()
        activityCloseTransition(R.anim.anim_from_left, R.anim.anim_to_right)
    }

    companion object {
        const val UPDATE = "update"
        const val BOOK_COMMENT = "book_comment"
        const val RECOMMEND = "recommend"
        const val CREATE = "create"
        const val BOOK_INFO = "book_info"
        const val LOCATION = "location"
        const val FRIEND_INFO = "friend_info"
        const val BOOK_ID = "book_id"
        const val NOTIFICATION = "notification"
    }
}
