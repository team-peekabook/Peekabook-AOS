package com.sopt.peekabookaos.presentation.bookshelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogBlockBinding
import com.sopt.peekabookaos.util.dialog.ConfirmClickListener
import com.sopt.peekabookaos.util.dialog.WarningDialogFragment
import com.sopt.peekabookaos.util.extensions.getParcelableCompat
import timber.log.Timber

class BlockDialog : DialogFragment() {
    private var _binding: DialogBlockBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogBlockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        initLayout()
        initWarningDialogContent()
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

    private fun initWarningDialogContent() {
        val follower = arguments?.getString(FOLLOWER) ?: DEFAULT
        binding.block = BlockDialogContent().getBlock(requireContext(), follower)
    }

    private fun initConfirmBtnClickListener() {
        binding.btnBlockDialogConfirm.setOnClickListener {
            arguments?.getParcelableCompat<ConfirmClickListener>(WarningDialogFragment.CONFIRM_ACTION)
                ?.onConfirmClick()
                ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            dismiss()
        }
    }

    private fun initCancelBtnClickListener() {
        binding.btnBlockDialogCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "BlockDialogFragment"
        const val FOLLOWER = "follower"
        const val DEFAULT = "default"
    }
}
