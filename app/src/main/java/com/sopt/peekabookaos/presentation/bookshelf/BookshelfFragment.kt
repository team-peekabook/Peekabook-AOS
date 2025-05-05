package com.sopt.peekabookaos.presentation.bookshelf

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentBookshelfBinding
import com.sopt.peekabookaos.presentation.book.BookActivity
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_ID
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.CREATE
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.FRIEND_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.detail.DetailActivity
import com.sopt.peekabookaos.presentation.detail.DetailActivity.Companion.FRIEND_SHELF
import com.sopt.peekabookaos.presentation.detail.DetailActivity.Companion.MY_SHELF
import com.sopt.peekabookaos.presentation.notification.NotificationActivity
import com.sopt.peekabookaos.presentation.pickModify.PickModifyActivity
import com.sopt.peekabookaos.presentation.report.ReportActivity
import com.sopt.peekabookaos.presentation.report.ReportActivity.Companion.FRIEND_ID
import com.sopt.peekabookaos.presentation.search.user.SearchUserActivity
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.dialog.WarningType
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import com.sopt.peekabookaos.util.extensions.withArgs
import dagger.hilt.android.AndroidEntryPoint

private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

@AndroidEntryPoint
class BookshelfFragment : BindingFragment<FragmentBookshelfBinding>(R.layout.fragment_bookshelf) {
    private val myShelfAdapter: BookShelfShelfAdapter?
        get() = binding.rvBookshelfBottomViewShelf.adapter as? BookShelfShelfAdapter
    private val pickAdapter: BookShelfPickAdapter?
        get() = binding.rvBookshelfPick.adapter as? BookShelfPickAdapter
    private val friendAdapter: BookShelfFriendAdapter?
        get() = binding.rvBookshelfFriendList.adapter as? BookShelfFriendAdapter
    private lateinit var shelfItemDeco: BookshelfShelfDecoration
    private lateinit var pickItemDeco: BookshelfPickDecoration
    private val viewModel by viewModels<BookShelfViewModel>()

    override fun onResume() {
        super.onResume()
        if (viewModel.isMyServerStatus.value == true) {
            viewModel.getMyShelfData()
            viewModel.updateShelfState(USER)
            friendAdapter?.clearSelection()
            binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S1Bd)
        } else if (viewModel.isFriendServerStatus.value == true) {
            viewModel.getFriendShelfData()
            friendAdapter?.submitList(viewModel.friendUserData.value)
            friendAdapter?.updateSelectedPosition(viewModel.lastSelectedItem.value!!)
            binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S2Md)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initIsServerObserver()
        initDataObserver()
        initAdapter()
        initItemDecoration()
        initUserClickListener()
        initFriendPlusClickListener()
        initNotificationClickListener()
        initRecommendClickListener()
        initPickModifyClickListener()
        initCreateBookClickListener()
        initKebabClickListener()
    }

    private fun initIsServerObserver() {
        viewModel.isMyServerStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                myShelfAdapter?.submitList(viewModel.shelfData.value)
                pickAdapter?.submitList(viewModel.pickData.value)
                friendAdapter?.submitList(viewModel.friendUserData.value)
            }
        }

        viewModel.isFriendServerStatus.observe(viewLifecycleOwner) {
            if (viewModel.isFriendServerStatus.value == false && viewModel.isMyServerStatus.value == false) {
                updateToMyShelf()
            }
        }

        viewModel.isBlockStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                updateToMyShelf()
            }
        }

        viewModel.isUnfollowStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                updateToMyShelf()
            }
        }
    }

    private fun initDataObserver() {
        viewModel.shelfData.observe(viewLifecycleOwner) {
            myShelfAdapter?.submitList(viewModel.shelfData.value)
        }
        viewModel.pickData.observe(viewLifecycleOwner) {
            pickAdapter?.submitList(viewModel.pickData.value)
        }
        viewModel.friendUserData.observe(viewLifecycleOwner) {
            friendAdapter?.submitList(viewModel.friendUserData.value)
        }
    }

    private fun initAdapter() {
        binding.rvBookshelfBottomViewShelf.adapter = BookShelfShelfAdapter { _, item ->
            val toDetail = Intent(requireActivity(), DetailActivity::class.java)
            toDetail.putExtra(BOOK_ID, item.id)
            if (viewModel.isMyServerStatus.value == true) {
                toDetail.putExtra(LOCATION, MY_SHELF)
            } else if (viewModel.isFriendServerStatus.value == true) {
                toDetail.putExtra(LOCATION, FRIEND_SHELF)
            }
            startActivity(toDetail)
        }
        binding.rvBookshelfPick.adapter = BookShelfPickAdapter { _, item ->
            val toDetail = Intent(requireActivity(), DetailActivity::class.java)
            toDetail.putExtra(BOOK_ID, item.id)
            if (viewModel.isMyServerStatus.value == true) {
                toDetail.putExtra(LOCATION, MY_SHELF)
            } else if (viewModel.isFriendServerStatus.value == true) {
                toDetail.putExtra(LOCATION, FRIEND_SHELF)
            }
            startActivity(toDetail)
        }
        binding.rvBookshelfFriendList.adapter = BookShelfFriendAdapter { pos, item ->
            viewModel.updateUserId(item)
            viewModel.getFriendShelfData()
            viewModel.updateLastSelectedItem(pos)
            viewModel.updateShelfState(FRIEND)
            friendAdapter?.updateSelectedPosition(pos)
            binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S2Md)
        }
    }

    private fun initItemDecoration() {
        shelfItemDeco = BookshelfShelfDecoration(requireContext())
        binding.rvBookshelfBottomViewShelf.addItemDecoration(shelfItemDeco)
        pickItemDeco = BookshelfPickDecoration(requireContext())
        binding.rvBookshelfPick.addItemDecoration(pickItemDeco)
    }

    private fun initUserClickListener() {
        binding.ivBookshelfUserProfile.setSingleOnClickListener {
            updateToMyShelf()
        }
    }

    private fun initFriendPlusClickListener() {
        binding.btnBookshelfFriendPlus.setSingleOnClickListener {
            val toSearchUser = Intent(requireActivity(), SearchUserActivity::class.java)
            toSearchUser.putExtra(LOCATION, CREATE)
            startActivity(toSearchUser)
        }
    }

    private fun initNotificationClickListener() {
        binding.btnBookshelfNotification.setSingleOnClickListener {
            val toNotification = Intent(requireActivity(), NotificationActivity::class.java)
            startActivity(toNotification)
        }
    }

    private fun initRecommendClickListener() {
        binding.btnBookshelfRecommend.setSingleOnClickListener {
            val toSearchBook = Intent(requireActivity(), BookActivity::class.java).apply {
                putExtra(FRIEND_INFO, viewModel.friendData.value)
            }
            startActivity(toSearchBook)
        }
    }

    private fun initPickModifyClickListener() {
        binding.btnBookshelfPickModify.setSingleOnClickListener {
            val toPickModify = Intent(requireActivity(), PickModifyActivity::class.java)
            startActivity(toPickModify)
        }
    }

    private fun initCreateBookClickListener() {
        binding.btnBookshelfAddBook.setSingleOnClickListener {
            startActivity(Intent(requireActivity(), BookActivity::class.java))
        }
    }

    private fun initKebabClickListener() {
        binding.btnBookshelfMore.setSingleOnClickListener {
            val bookShelfBottomSheetFragment =
                BookShelfBottomSheetFragment.onItemClick { itemClick ->
                    when (itemClick) {
                        0 -> initUnfollowDialog()
                        1 -> initReport()
                        2 -> initBlockDialog()
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

    private fun updateToMyShelf() {
        viewModel.getMyShelfData()
        viewModel.updateShelfState(USER)
        friendAdapter?.clearSelection()
        binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S1Bd)
    }

    companion object {
        private const val FRIEND = true
        private const val USER = false
    }
}
