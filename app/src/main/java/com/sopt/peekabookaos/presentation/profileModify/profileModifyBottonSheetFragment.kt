package com.sopt.peekabookaos.presentation.profileModify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sopt.peekabookaos.databinding.DialogProfileModifyBottonSheetBinding
import com.sopt.peekabookaos.presentation.userInput.UserInputBottomSheetFragment

class ProfileModifyBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: DialogProfileModifyBottonSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogProfileModifyBottonSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvProfileModifyBottomSheetAlbum.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }
        binding.tvProfileModifyBottomSheetCamera.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
        binding.tvProfileModifyBottomSheetBasic.setOnClickListener {
            itemClick(2)
            dialog?.dismiss()
        }
    }

    companion object {
        lateinit var itemClick: (Int) -> Unit
        fun onItemClick(
            itemClick: (Int) -> Unit
        ): ProfileModifyBottomSheetFragment {
            this.itemClick = itemClick
            return ProfileModifyBottomSheetFragment()
        }
    }
}

