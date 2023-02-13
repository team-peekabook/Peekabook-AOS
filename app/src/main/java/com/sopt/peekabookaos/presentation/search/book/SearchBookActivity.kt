package com.sopt.peekabookaos.presentation.search.book

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.SelfIntro
import com.sopt.peekabookaos.databinding.ActivitySearchBookBinding
import com.sopt.peekabookaos.presentation.recommendation.RecommendationActivity
import com.sopt.peekabookaos.presentation.recommendation.RecommendationActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.recommendation.RecommendationActivity.Companion.FRIEND_INFO
import com.sopt.peekabookaos.presentation.updateBook.UpdateBookActivity
import com.sopt.peekabookaos.presentation.updateBook.UpdateBookActivity.Companion.BOOK
import com.sopt.peekabookaos.presentation.updateBook.UpdateBookActivity.Companion.CREATE
import com.sopt.peekabookaos.presentation.updateBook.UpdateBookActivity.Companion.LOCATION
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.KeyBoardUtil
import com.sopt.peekabookaos.util.extensions.getParcelable
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBookActivity :
    BindingActivity<ActivitySearchBookBinding>(R.layout.activity_search_book) {
    private val searchBookViewModel: SearchBookViewModel by viewModels()
    private lateinit var searchBookAdapter: SearchBookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = searchBookViewModel
        initView()
        initSearchBookAdapter()
        initEditTextClearFocus()
        initKeyboardDoneClickListener()
        initCloseBtnClickListener()
        collectServerStatusState()
    }

    private fun initView() {
        when (intent.getStringExtra(LOCATION) ?: CREATE) {
            RECOMMEND -> {
                searchBookViewModel.updateUiState(
                    friendInfo = intent.getParcelable(FRIEND_INFO, SelfIntro::class.java)!!,
                    isCreateView = false
                )
            }
            else -> {
                searchBookViewModel.updateUiState(friendInfo = SelfIntro(), isCreateView = true)
            }
        }
    }

    private fun initSearchBookAdapter() {
        searchBookAdapter = SearchBookAdapter(onClickBook = ::onClickBook, text = initAdapterText())
        binding.rvSearchBook.adapter = searchBookAdapter
    }

    private fun onClickBook(book: Book) {
        if (searchBookViewModel.uiState.value.isCreateView) {
            Intent(this, UpdateBookActivity::class.java).apply {
                putExtra(BOOK, book)
                putExtra(LOCATION, CREATE)
            }.also { intent ->
                startActivity(intent)
                finish()
            }
        } else {
            Intent(this, RecommendationActivity::class.java).apply {
                putExtra(BOOK_INFO, book)
                putExtra(FRIEND_INFO, searchBookViewModel.uiState.value.friendInfo)
            }.also { intent ->
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initAdapterText(): String {
        return if (searchBookViewModel.uiState.value.isCreateView) {
            getString(R.string.search_book_add)
        } else {
            getString(R.string.search_book_recommend)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clSearchBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }

        binding.rvSearchBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }

        binding.btnSearchBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }
    }

    private fun initKeyboardDoneClickListener() {
        binding.etSearchBook.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnSearchBook.performClick()
                handled = true
            }
            KeyBoardUtil.hide(activity = this)
            handled
        }
    }

    private fun initCloseBtnClickListener() {
        binding.btnSearchBookClose.setSingleOnClickListener {
            finish()
        }
    }

    private fun collectServerStatusState() {
        repeatOnStarted {
            searchBookViewModel.isSearch.collect { success ->
                if (success) {
                    binding.llSearchBookError.visibility = View.INVISIBLE
                    binding.rvSearchBook.visibility = View.VISIBLE
                    searchBookAdapter.submitList(searchBookViewModel.uiState.value.book)
                } else {
                    binding.llSearchBookError.visibility = View.VISIBLE
                    binding.rvSearchBook.visibility = View.INVISIBLE
                }
            }
        }
    }

    companion object {
        const val RECOMMEND = "recommend"
    }
}
