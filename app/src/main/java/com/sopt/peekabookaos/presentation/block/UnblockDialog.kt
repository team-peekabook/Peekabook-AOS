package com.sopt.peekabookaos.presentation.block

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogUnblockBinding

class UnblockDialog : DialogFragment() {
    private var _binding: DialogUnblockBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val blockViewModel by activityViewModels<BlockViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogUnblockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = blockViewModel
        binding.index = arguments?.getInt(BLOCK_INDEX) ?: DEFAULT
        isCancelable = true
        initLayout()
        initConfirmBtnClickListener()
        initCancelBtnClickListener()
    }

    private fun initLayout() {
        val ratio = 0.89
        val layoutParams = requireNotNull(dialog).window!!.attributes
        layoutParams.width = (resources.displayMetrics.widthPixels * ratio).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        requireNotNull(dialog).window!!.attributes = layoutParams
    }

    private fun initConfirmBtnClickListener() {
        val index = arguments?.getInt(BLOCK_INDEX) ?: DEFAULT
        binding.btnBlockDeleteDialogConfirm.setOnClickListener {
            blockViewModel.deleteBlock(requireNotNull(blockViewModel.blockUser.value)[index].id)
            dismiss()
        }
    }

    private fun initCancelBtnClickListener() {
        binding.btnBlockDeleteDialogCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "UnblockDialogFragment"
        const val BLOCK_INDEX = "block_index"
        private const val DEFAULT = -1
    }
}
