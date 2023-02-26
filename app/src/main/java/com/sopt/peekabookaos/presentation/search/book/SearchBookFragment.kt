package com.sopt.peekabookaos.presentation.search.book

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.SelfIntro
import com.sopt.peekabookaos.databinding.FragmentSearchBookBinding
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.CREATE
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.FRIEND_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.RECOMMEND
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.KeyBoardUtil
import com.sopt.peekabookaos.util.extensions.getParcelable
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchBookFragment :
    BindingFragment<FragmentSearchBookBinding>(R.layout.fragment_search_book) {
    private val searchBookViewModel: SearchBookViewModel by viewModels()
    private var searchBookAdapter: SearchBookAdapter? = null
    private val bundle = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = searchBookViewModel
        initLayout()
        initSearchBookAdapter()
        initEditTextClearFocus()
        initKeyboardDoneClickListener()
        initCloseBtnClickListener()
        collectServerState()
    }

    private fun initLayout() {
        when (requireActivity().intent.getStringExtra(LOCATION) ?: CREATE) {
            RECOMMEND -> {
                searchBookViewModel.updateUiState(
                    friendInfo = requireNotNull(
                        requireActivity().intent.getParcelable(FRIEND_INFO, SelfIntro::class.java)
                    ),
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
            findNavController().navigate(
                R.id.action_searchBookFragment_to_createBookFragment,
                bundle.apply {
                    putParcelable(BOOK, book)
                    putString(LOCATION, CREATE)
                }
            )
        } else {
            findNavController().navigate(
                R.id.action_searchBookFragment_to_recommendationFragment,
                bundle.apply {
                    putParcelable(BOOK, book)
                    putParcelable(FRIEND_INFO, searchBookViewModel.uiState.value.friendInfo)
                }
            )
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
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }

        binding.rvSearchBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }

        binding.btnSearchBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
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
            KeyBoardUtil.hide(activity = requireActivity())
            handled
        }
    }

    private fun initCloseBtnClickListener() {
        binding.btnSearchBookClose.setOnClickListener {
            activity?.finish() ?: Timber.e(getString(R.string.null_point_exception))
        }
    }

    private fun collectServerState() {
        repeatOnStarted {
            searchBookViewModel.isSearch.collect { success ->
                if (success) {
                    binding.llSearchBookError.visibility = View.INVISIBLE
                    binding.rvSearchBook.visibility = View.VISIBLE
                    searchBookAdapter?.submitList(searchBookViewModel.uiState.value.book)
                } else {
                    binding.llSearchBookError.visibility = View.VISIBLE
                    binding.rvSearchBook.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchBookAdapter = null
    }
}
