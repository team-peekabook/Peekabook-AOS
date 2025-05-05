package com.sopt.peekabookaos.presentation.notificationBookshelf

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentNotificationBookshelfBinding
import com.sopt.peekabookaos.domain.entity.Notification
import com.sopt.peekabookaos.presentation.block.BlockDialog
import com.sopt.peekabookaos.presentation.book.BookActivity
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_ID
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.FRIEND_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.NOTIFICATION
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.RECOMMEND
import com.sopt.peekabookaos.presentation.bookshelf.FollowBookShelfBottomSheetFragment
import com.sopt.peekabookaos.presentation.detail.DetailActivity
import com.sopt.peekabookaos.presentation.main.MainActivity
import com.sopt.peekabookaos.presentation.notificationBookshelf.follow.FollowDialog
import com.sopt.peekabookaos.presentation.notificationBookshelf.follow.FollowDialog.Companion.NICKNAME
import com.sopt.peekabookaos.presentation.report.ReportActivity
import com.sopt.peekabookaos.presentation.report.ReportActivity.Companion.FRIEND_ID
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.dialog.WarningType
import com.sopt.peekabookaos.util.extensions.getParcelableCompat
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationBookshelfFragment :
    BindingFragment<FragmentNotificationBookshelfBinding>(R.layout.fragment_notification_bookshelf) {
    private val myShelfAdapter: NotificationBookShelfShelfAdapter?
        get() = binding.rvNotificationBookshelfBottomViewShelf.adapter as? NotificationBookShelfShelfAdapter
    private val pickAdapter: NotificationBookShelfPickAdapter?
        get() = binding.rvNotificationBookshelfPick.adapter as? NotificationBookShelfPickAdapter
    private lateinit var shelfItemDeco: NotificationBookshelfShelfDecoration
    private lateinit var pickItemDeco: NotificationBookshelfPickDecoration
    private val viewModel by viewModels<NotificationBookShelfViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initUserInfo()
        initIsServerObserver()
        initDataObserver()
        initAdapter()
        initItemDecoration()
        initRecommendClickListener()
        initFollowClickListener()
        initKebabClickListener()
        initBackBtnOnClickListener()
    }

    private fun initUserInfo() {
        viewModel.initUserInfo(
            arguments?.getParcelableCompat(NOTIFICATION) ?: Notification()
        )
    }

    private fun initIsServerObserver() {
        viewModel.isBlockStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                moveToMain()
            }
        }

        viewModel.isUnfollowStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                moveToMain()
            }
        }

        viewModel.isFollowSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                val dialog = FollowDialog().apply {
                    arguments = Bundle().apply {
                        putString(NICKNAME, viewModel.friendData.value?.nickname)
                    }
                }
                dialog.show(parentFragmentManager, FollowDialog.TAG)
            }
        }
    }

    private fun moveToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }

    private fun initDataObserver() {
        viewModel.shelfData.observe(viewLifecycleOwner) {
            myShelfAdapter?.submitList(viewModel.shelfData.value)
        }
        viewModel.pickData.observe(viewLifecycleOwner) {
            pickAdapter?.submitList(viewModel.pickData.value)
        }
    }

    private fun initAdapter() {
        binding.rvNotificationBookshelfBottomViewShelf.adapter =
            NotificationBookShelfShelfAdapter { _, item ->
                val toDetail = Intent(requireActivity(), DetailActivity::class.java)
                toDetail.putExtra(BOOK_ID, item.id)
                startActivity(toDetail)
            }
        binding.rvNotificationBookshelfPick.adapter = NotificationBookShelfPickAdapter { _, item ->
            val toDetail = Intent(requireActivity(), DetailActivity::class.java)
            toDetail.putExtra(BOOK_ID, item.id)
            startActivity(toDetail)
        }
    }

    private fun initItemDecoration() {
        shelfItemDeco = NotificationBookshelfShelfDecoration(requireContext())
        binding.rvNotificationBookshelfBottomViewShelf.addItemDecoration(shelfItemDeco)
        pickItemDeco = NotificationBookshelfPickDecoration(requireContext())
        binding.rvNotificationBookshelfPick.addItemDecoration(pickItemDeco)
    }

    private fun initRecommendClickListener() {
        binding.btnNotificationBookshelfRecommend.setSingleOnClickListener {
            val toSearchBook = Intent(requireActivity(), BookActivity::class.java).apply {
                putExtra(FRIEND_INFO, viewModel.friendData.value)
                putExtra(LOCATION, RECOMMEND)
            }
            startActivity(toSearchBook)
        }
    }

    private fun initFollowClickListener() {
        binding.btnNotificationBookshelfFollow.setSingleOnClickListener {
            viewModel.postFollow()
        }
    }

    private fun initKebabClickListener() {
        binding.btnNotificationBookshelfMore.setSingleOnClickListener {
            val bookShelfBottomSheetFragment = if (viewModel.isFollow.value == true) {
                FollowBookShelfBottomSheetFragment.onItemClick { itemClick ->
                    when (itemClick) {
                        0 -> initUnfollowDialog()
                        1 -> initReport()
                        2 -> initBlockDialog()
                    }
                }
            } else {
                UnfollowBookShelfBottomSheetFragment.onItemClick { itemClick ->
                    when (itemClick) {
                        1 -> initReport()
                        2 -> initBlockDialog()
                    }
                }
            }

            bookShelfBottomSheetFragment.show(
                parentFragmentManager,
                bookShelfBottomSheetFragment.tag
            )
        }
    }

    private fun initUnfollowDialog() {
        WarningDialogFragment().withArgs {
            putString(
                WarningDialogFragment.FOLLOWER,
                requireNotNull(viewModel.friendData.value).nickname
            )
            putSerializable(
                WarningDialogFragment.WARNING_TYPE,
                WarningType.WARNING_UNFOLLOW
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = {
                    viewModel.postUnfollow()
                })
            )
        }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }

    private fun initBlockDialog() {
        BlockDialog().withArgs {
            putString(
                BlockDialog.FOLLOWER,
                requireNotNull(viewModel.friendData.value).nickname
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = {
                    viewModel.postBlock()
                })
            )
        }.show(childFragmentManager, BlockDialog.TAG)
    }

    private fun initReport() {
        val toReport = Intent(requireActivity(), ReportActivity::class.java)
        toReport.putExtra(FRIEND_ID, requireNotNull(viewModel.friendData.value).id)
        startActivity(toReport)
    }

    private fun initBackBtnOnClickListener() {
        binding.btnNotificationBookshelfBack.setOnClickListener { findNavController().popBackStack() }
    }
}
