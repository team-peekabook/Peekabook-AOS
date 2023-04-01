package com.sopt.peekabookaos.presentation.userInput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogUserInputBottomSheetBinding

class UserInputBottomSheetFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogUserInputBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.dialog_user_input_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvUserInputBottomSheetChooseAlbum.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }
        binding.tvUserInputBottomSheetCamera.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
    }
}
