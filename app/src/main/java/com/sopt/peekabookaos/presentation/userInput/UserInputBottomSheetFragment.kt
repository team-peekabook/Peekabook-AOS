package com.sopt.peekabookaos.presentation.userInput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sopt.peekabookaos.databinding.DialogProfileBottomSheetBinding

class UserInputBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: DialogProfileBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogProfileBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvProfileBottomSheetChooseAlbum.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }
        binding.tvProfileBottomSheetBasicImage.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
    }

    companion object {
        lateinit var itemClick: (Int) -> Unit
        fun onItemClick(
            itemClick: (Int) -> Unit
        ): UserInputBottomSheetFragment {
            this.itemClick = itemClick
            return UserInputBottomSheetFragment()
        }
    }
}
