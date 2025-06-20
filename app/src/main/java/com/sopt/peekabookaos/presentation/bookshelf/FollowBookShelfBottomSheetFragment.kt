package com.sopt.peekabookaos.presentation.bookshelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sopt.peekabookaos.databinding.DialogFollowBookshelfBottomSheetBinding

class FollowBookShelfBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: DialogFollowBookshelfBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFollowBookshelfBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetClickListener()
    }

    private fun bottomSheetClickListener() {
        binding.tvBookshelfBottomSheetUnfollow.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }
        binding.tvBookshelfBottomSheetReport.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
        binding.tvBookshelfBottomSheetBlock.setOnClickListener {
            itemClick(2)
            dialog?.dismiss()
        }
    }

    companion object {
        lateinit var itemClick: (Int) -> Unit
        fun onItemClick(
            itemClick: (Int) -> Unit
        ): FollowBookShelfBottomSheetFragment {
            this.itemClick = itemClick
            return FollowBookShelfBottomSheetFragment()
        }
    }
}
