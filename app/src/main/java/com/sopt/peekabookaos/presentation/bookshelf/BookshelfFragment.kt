package com.sopt.peekabookaos.presentation.bookshelf

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentBookshelfBinding
import com.sopt.peekabookaos.presentation.book.BookActivity
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.CREATE
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.FRIEND_INFO
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.book.BookActivity.Companion.RECOMMEND
import com.sopt.peekabookaos.presentation.detail.DetailActivity
import com.sopt.peekabookaos.presentation.detail.DetailActivity.Companion.FRIEND_SHELF
import com.sopt.peekabookaos.presentation.detail.DetailActivity.Companion.MY_SHELF
import com.sopt.peekabookaos.presentation.notification.NotificationActivity
import com.sopt.peekabookaos.presentation.pickModify.PickModifyActivity
import com.sopt.peekabookaos.presentation.search.user.SearchUserActivity
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

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
                viewModel.getMyShelfData()
                viewModel.updateShelfState(USER)
                friendAdapter?.clearSelection()
                binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S1Bd)
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
            toDetail.putExtra(BOOK_INFO, item.id)
            if (viewModel.isMyServerStatus.value == true) {
                toDetail.putExtra(LOCATION, MY_SHELF)
            } else if (viewModel.isFriendServerStatus.value == true) {
                toDetail.putExtra(LOCATION, FRIEND_SHELF)
            }
            startActivity(toDetail)
        }
        binding.rvBookshelfPick.adapter = BookShelfPickAdapter { _, item ->
            val toDetail = Intent(requireActivity(), DetailActivity::class.java)
            toDetail.putExtra(BOOK_INFO, item.id)
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
            viewModel.getMyShelfData()
            viewModel.updateShelfState(USER)
            friendAdapter?.clearSelection()
            binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S1Bd)
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
            val toSearchBook = Intent(requireActivity(), SearchBookActivity::class.java)
            toSearchBook.putExtra(FRIEND_INFO, viewModel.friendData.value)
            toSearchBook.putExtra(LOCATION, RECOMMEND)
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
            val toBarcodeScanner = Intent(requireActivity(), BookActivity::class.java)
            startActivity(toBarcodeScanner)
        }
    }

    companion object {
        private const val FRIEND = true
        private const val USER = false
    }
}
