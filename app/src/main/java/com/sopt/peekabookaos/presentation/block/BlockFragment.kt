package com.sopt.peekabookaos.presentation.block

import androidx.fragment.app.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentBlockBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockFragment : BindingFragment<FragmentBlockBinding>(R.layout.fragment_block) {
    private val blockViewModel: BlockViewModel by viewModels()
}
