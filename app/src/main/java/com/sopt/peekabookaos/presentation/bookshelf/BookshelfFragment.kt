package com.sopt.peekabookaos.presentation.bookshelf

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentBookshelfBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookshelfFragment : BindingFragment<FragmentBookshelfBinding>(R.layout.fragment_bookshelf) {
    private lateinit var myShelfAdapter: BookShelfShelfAdapter
    private lateinit var pickAdapter: BookShelfPickAdapter
    private lateinit var friendAdapter: BookShelfFriendAdapter
    private lateinit var itemDeco: BookshelfShelfDecoration
    private val viewModel by viewModels<BookShelfViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initAdapter()
        initItemDecoration()
        initUserClickListener()
        initObserver()
    }

    private fun initAdapter() {
        myShelfAdapter = BookShelfShelfAdapter()
        binding.rvBookshelfBottomViewShelf.adapter = myShelfAdapter
        myShelfAdapter.submitList(viewModel.shelfData.value)

        pickAdapter = BookShelfPickAdapter()
        binding.rvBookshelfPick.adapter = pickAdapter
        pickAdapter.submitList(viewModel.pickData.value)

        friendAdapter = BookShelfFriendAdapter { pos, _ ->
            viewModel.updateShelfState(FRIEND)
            viewModel.updateUserId(pos)
            binding.ivBookshelfUserProfileRedline.visibility = View.INVISIBLE
            binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S2Md)
        }
        binding.rvBookshelfFriendList.adapter = friendAdapter
        friendAdapter.submitList(viewModel.friendUserData.value)
    }

    private fun initItemDecoration() {
        itemDeco = BookshelfShelfDecoration(requireContext())
        binding.rvBookshelfBottomViewShelf.addItemDecoration(itemDeco)
    }

    private fun initUserClickListener() {
        binding.ivBookshelfUserProfile.setOnClickListener {
            binding.ivBookshelfUserProfileRedline.visibility = View.VISIBLE
            viewModel.updateShelfState(USER)
            friendAdapter.clearSelection()
            binding.tvBookshelfUserProfileName.setTextAppearance(R.style.S1Bd)
        }
    }

    private fun initObserver() {
        viewModel.userId.observe(viewLifecycleOwner) {
            if (viewModel.friendShelf.value == FRIEND) {
                friendAdapter.updateSelectedPosition(it)
            }
        }
    }

    companion object {
        const val FRIEND = true
        const val USER = false
    }
}
