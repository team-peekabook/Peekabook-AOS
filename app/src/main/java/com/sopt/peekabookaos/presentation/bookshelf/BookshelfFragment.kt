package com.sopt.peekabookaos.presentation.bookshelf

import android.os.Bundle
import android.view.View
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentBookshelfBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookshelfFragment : BindingFragment<FragmentBookshelfBinding>(R.layout.fragment_bookshelf) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
