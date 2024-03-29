package com.sopt.peekabookaos.presentation.createBook

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentCreateBookBinding
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_ID
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.CREATE
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.detail.DetailActivity
import com.sopt.peekabookaos.util.KeyBoardUtil
import com.sopt.peekabookaos.util.UiEvent
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.dialog.WarningType
import com.sopt.peekabookaos.util.extensions.getParcelableCompat
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateBookFragment :
    BindingFragment<FragmentCreateBookBinding>(R.layout.fragment_create_book) {
    private val createBookViewModel: CreateBookViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = createBookViewModel
        initBookInfo()
        checkBookDuplicate()
        collectBookDuplicate()
        initEditTextClearFocus()
        initBackBtnOnClickListener()
        collectUiEvent()
    }

    private fun checkBookDuplicate() = createBookViewModel.postBookDuplicate()

    private fun collectBookDuplicate() {
        repeatOnStarted {
            createBookViewModel.isBookDuplicated.collect { isDuplicated ->
                if (isDuplicated) {
                    showBookDuplicateDialog()
                }
            }
        }
    }

    private fun showBookDuplicateDialog() {
        WarningDialogFragment().withArgs {
            putSerializable(
                WarningDialogFragment.WARNING_TYPE,
                WarningType.WARNING_BOOK_DUPLICATE
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = { requireActivity().finish() })
            )
        }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clCreateBook.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }

        binding.btnCreateBookSave.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }
    }

    private fun initBookInfo() {
        createBookViewModel.initBookInfo(
            arguments?.getParcelableCompat(BOOK_INFO) ?: Book()
        )
    }

    private fun initBackBtnOnClickListener() {
        binding.btnCreateBookBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            createBookViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.IDLE -> {
                        binding.btnCreateBookSave.isEnabled = false
                    }

                    UiEvent.SUCCESS -> {
                        startActivity(
                            Intent(requireActivity(), DetailActivity::class.java).apply {
                                putExtra(LOCATION, CREATE)
                                putExtra(BOOK_ID, createBookViewModel.bookInfo.value.id)
                            }
                        )
                        requireActivity().finish()
                    }

                    UiEvent.ERROR -> {
                        binding.btnCreateBookSave.isEnabled = true
                    }
                }
            }
        }
    }
}
