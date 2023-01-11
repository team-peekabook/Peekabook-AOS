package com.sopt.peekabookaos.presentation.bookshelf

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentBookshelfBinding
import com.sopt.peekabookaos.presentation.barcodeScanner.BarcodeScannerActivity
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.detail.DetailActivity
import com.sopt.peekabookaos.presentation.detail.DetailActivity.Companion.BOOK_INFO
import com.sopt.peekabookaos.presentation.detail.DetailActivity.Companion.MY
import com.sopt.peekabookaos.presentation.notification.NotificationActivity
import com.sopt.peekabookaos.presentation.pickModify.PickModifyActivity
import com.sopt.peekabookaos.presentation.recommendation.RecommendationActivity.Companion.FRIEND_INFO
import com.sopt.peekabookaos.presentation.search.user.SearchUserActivity
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookshelfFragment : BindingFragment<FragmentBookshelfBinding>(R.layout.fragment_bookshelf) {
    private val myShelfAdapter: BookShelfShelfAdapter?
        get() = binding.rvBookshelfBottomViewShelf.adapter as? BookShelfShelfAdapter
    private val pickAdapter: BookShelfPickAdapter?
        get() = binding.rvBookshelfPick.adapter as? BookShelfPickAdapter
    private val friendAdapter: BookShelfFriendAdapter?
        get() = binding.rvBookshelfFriendList.adapter as? BookShelfFriendAdapter
    private lateinit var itemDeco: BookshelfShelfDecoration
    private val viewModel by viewModels<BookShelfViewModel>()

    override fun onResume() {
        super.onResume()
        viewModel.getMyShelfData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initIsServerObserver()
        initAdapter()
        initItemDecoration()
        initUserClickListener()
        initFriendPlusClickListener()
        initNotificationClickListener()
        initRecommendClickListener()
        initPickModifyClickListener()
        initCreateBookClickListener()
    }

    private fun initAdapter() {
        binding.rvBookshelfBottomViewShelf.adapter = BookShelfShelfAdapter { _, item ->
            val toDetail = Intent(requireActivity(), DetailActivity::class.java)
            toDetail.putExtra(BOOK_INFO, item.id)
            if (viewModel.isMyServerStatus.value == true) {
                toDetail.putExtra(LOCATION, MY)
            } else if (viewModel.isFriendServerStatus.value == true) {
                toDetail.putExtra(LOCATION, FRIEND)
            }
            startActivity(toDetail)
        }
        binding.rvBookshelfPick.adapter = BookShelfPickAdapter { _, item ->
            val toDetail = Intent(requireActivity(), DetailActivity::class.java)
            toDetail.putExtra(BOOK_INFO, item.book.id)
            if (viewModel.isMyServerStatus.value == true) {
                toDetail.putExtra(LOCATION, MY)
            } else if (viewModel.isFriendServerStatus.value == true) {
                toDetail.putExtra(LOCATION, FRIEND)
            }
            startActivity(toDetail)
        }
        binding.rvBookshelfFriendList.adapter = BookShelfFriendAdapter { pos, item ->
            viewModel.updateUserId(item)
            viewModel.getFriendShelfData()
            viewModel.updateShelfState(FRIEND)
            friendAdapter?.updateSelectedPosition(pos)
            binding.ivBookshelfUserProfileRedline.visibility = View.INVISIBLE
            binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S2Md)
        }
    }

    private fun initItemDecoration() {
        itemDeco = BookshelfShelfDecoration(requireContext())
        binding.rvBookshelfBottomViewShelf.addItemDecoration(itemDeco)
    }

    private fun initUserClickListener() {
        binding.ivBookshelfUserProfile.setOnClickListener {
            viewModel.getMyShelfData()
            binding.ivBookshelfUserProfileRedline.visibility = View.VISIBLE
            viewModel.updateShelfState(USER)
            friendAdapter?.clearSelection()
            binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S1Bd)
        }
    }

    private fun initFriendPlusClickListener() {
        binding.btnBookshelfFriendPlus.setOnClickListener {
            val toSearchUser = Intent(requireActivity(), SearchUserActivity::class.java)
            startActivity(toSearchUser)
        }
    }

    private fun initNotificationClickListener() {
        binding.btnBookshelfNotification.setOnClickListener {
            val toNotification = Intent(requireActivity(), NotificationActivity::class.java)
            startActivity(toNotification)
        }
    }

    private fun initRecommendClickListener() {
        binding.btnBookshelfRecommend.setOnClickListener {
            val toSearch = Intent(requireActivity(), BarcodeScannerActivity::class.java)
            toSearch.putExtra(FRIEND_INFO, viewModel.friendData.value)
            startActivity(toSearch)
        }
    }

    private fun initPickModifyClickListener() {
        binding.btnBookshelfPickModify.setOnClickListener {
            val toPickModify = Intent(requireActivity(), PickModifyActivity::class.java)
            startActivity(toPickModify)
        }
    }

    private fun initCreateBookClickListener() {
        binding.btnBookshelfAddBook.setOnClickListener {
            val toBarcodeScanner = Intent(requireActivity(), BarcodeScannerActivity::class.java)
            startActivity(toBarcodeScanner)
        }
    }

    private fun initIsServerObserver() {
        viewModel.isMyServerStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                myShelfAdapter?.submitList(viewModel.shelfData.value)
                pickAdapter?.submitList(viewModel.pickData.value)
                friendAdapter?.submitList(viewModel.friendUserData.value)
            }
        }
        viewModel.shelfData.observe(viewLifecycleOwner) {
            myShelfAdapter?.submitList(viewModel.shelfData.value)
        }
        viewModel.pickData.observe(viewLifecycleOwner) {
            pickAdapter?.submitList(viewModel.pickData.value)
        }
        viewModel.friendData.observe(viewLifecycleOwner) {
            updateFriendShelfText()
        }
        viewModel.userData.observe(viewLifecycleOwner) {
            updateMyShelfText()
        }
    }

    private fun updateFriendShelfText() {
        binding.tvBookshelfSelfIntroName.text = viewModel.friendData.value?.nickname ?: ""
        binding.tvBookshelfSelfIntroNameComment.text = viewModel.friendData.value?.intro ?: ""
    }

    private fun updateMyShelfText() {
        binding.tvBookshelfSelfIntroName.text = viewModel.userData.value?.nickname ?: ""
        binding.tvBookshelfSelfIntroNameComment.text = viewModel.userData.value?.intro ?: ""
    }

    companion object {
        const val FRIEND = true
        const val USER = false
    }
}
