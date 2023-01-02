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
    }

    private fun initAdapter() {
        myShelfAdapter = BookShelfShelfAdapter()
        binding.rvBookshelfBottomViewShelf.adapter = myShelfAdapter
        myShelfAdapter.submitList(viewModel.shelfData.value)

        pickAdapter = BookShelfPickAdapter()
        binding.rvBookshelfPick.adapter = pickAdapter
        pickAdapter.submitList(viewModel.pickData.value)

        friendAdapter = BookShelfFriendAdapter()
        binding.rvBookshelfFriendList.adapter = friendAdapter
        friendAdapter.submitList(viewModel.friendData.value)
    }

    private fun initItemDecoration() {
        itemDeco = BookshelfShelfDecoration(requireContext())
        binding.rvBookshelfBottomViewShelf.addItemDecoration(itemDeco)
    }
}
