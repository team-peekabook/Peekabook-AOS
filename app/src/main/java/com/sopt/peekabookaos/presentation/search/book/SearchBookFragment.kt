package com.sopt.peekabookaos.presentation.search.book

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentSearchBookBinding
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.CREATE
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.FRIEND_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.RECOMMEND
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.UiEvent
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.getParcelable
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

@AndroidEntryPoint
class SearchBookFragment :
    BindingFragment<FragmentSearchBookBinding>(R.layout.fragment_search_book) {
    private val searchBookViewModel: SearchBookViewModel by viewModels()
    private var searchBookAdapter: SearchBookAdapter? = null
    private val bundle = Bundle()
    private var loadedBooks: List<Book>? = null
    private var isViewCreated = false

    private val multiPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.entries.isEmpty()) {
                requestAllPermissions()
            } else {
                goToBarcodeScanner()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = searchBookViewModel
        isViewCreated = true
        initLayout()
        initSearchBookAdapter()
        initEditTextClearFocus()
        initKeyboardDoneClickListener()
        initCloseBtnClickListener()
        initBarcodeClickListener()
        collectUiEvent()
        initNoBookClickListener()
        initBackPressedCallback()
    }

    override fun onResume() {
        super.onResume()
        if (isViewCreated) {
            if (loadedBooks != null) {
                binding.llSearchBookError.visibility = View.INVISIBLE
                binding.rvSearchBook.visibility = View.VISIBLE
                searchBookAdapter?.submitList(loadedBooks)
            }
        }
        initSearchFocus()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        loadedBooks?.let {
            outState.putParcelableArrayList(LOADED_BOOKS, ArrayList(it))
        }
    }

    private fun initSearchFocus() {
        binding.etSearchBook.requestFocus()
        KeyBoardUtil.show(requireActivity())
    }

    private fun initLayout() {
        when (requireActivity().intent.getStringExtra(LOCATION) ?: CREATE) {
            RECOMMEND -> {
                searchBookViewModel.updateUiState(
                    friendInfo = requireNotNull(
                        requireActivity().intent.getParcelable(FRIEND_INFO, User::class.java)
                    ),
                    isCreateView = false
                )
            }

            else -> {
                searchBookViewModel.updateUiState(friendInfo = User(), isCreateView = true)
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
                    putParcelable(BOOK_INFO, book)
                }
            )
        } else {
            findNavController().navigate(
                R.id.action_searchBookFragment_to_recommendationFragment,
                bundle.apply {
                    putParcelable(BOOK_INFO, book)
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

        binding.btnSearchBookBarcodeScanner.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }
    }

    private fun initKeyboardDoneClickListener() {
        binding.etSearchBook.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchBookViewModel.searchOnClick()
                handled = true
            }
            KeyBoardUtil.hide(activity = requireActivity())
            handled
        }
    }

    private fun initCloseBtnClickListener() {
        binding.btnSearchBookClose.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun initBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }

    private fun initBarcodeClickListener() {
        binding.btnSearchBookBarcodeScanner.setOnClickListener {
            requestAllPermissions()
        }
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            searchBookViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.IDLE -> {}

                    UiEvent.SUCCESS -> {
                        binding.llSearchBookError.isVisible = false
                        binding.clSearchBookNoBook.isVisible = false
                        binding.rvSearchBook.isVisible = true
                        searchBookAdapter?.submitList(searchBookViewModel.uiState.value.book) {
                            searchBookAdapter?.showFooter = true
                            binding.rvSearchBook.scrollToPosition(0)
                        }
                        loadedBooks = searchBookViewModel.uiState.value.book
                    }

                    UiEvent.ERROR -> {
                        binding.llSearchBookError.isVisible = true
                        binding.clSearchBookNoBook.isVisible = true
                        binding.rvSearchBook.isVisible = false
                    }
                }
            }
        }
    }

    private fun initNoBookClickListener() {
        binding.clSearchBookNoBook.setOnClickListener {
            goToWalla()
        }
    }

    private fun goToWalla() {
        val url = "https://walla.my/v/1g1JvcCRxDwmAeTM6xZw"
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    private fun goToBarcodeScanner() {
        findNavController().navigate(R.id.action_searchBookFragment_to_barcodeScannerFragment)
    }

    private fun requestAllPermissions() {
        multiPermissionCallback.launch(REQUIRED_PERMISSIONS)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchBookAdapter = null
    }

    companion object {
        private const val LOADED_BOOKS = "loaded_books"
    }
}
