package com.sopt.peekabookaos.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogWarningBinding
import com.sopt.peekabookaos.util.extensions.getParcelableCompat
import com.sopt.peekabookaos.util.extensions.getSerializableCompat
import com.sopt.peekabookaos.util.extensions.initLayout
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import timber.log.Timber

class WarningDialogFragment : DialogFragment() {
    private var _binding: DialogWarningBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogWarningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        initLayout()
        initWarningDialogContent()
        initConfirmClickListener()
        initCancelBtnClickListener()
    }

    private fun initWarningDialogContent() {
        val warningType = arguments?.getSerializableCompat(WARNING_TYPE) as? WarningType
            ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
        val follower = arguments?.getString(FOLLOWER) ?: DEFAULT
        with(binding) {
            warning = when (warningType as WarningType) {
                WarningType.WARNING_RECOMMEND ->
                    WarningDialogContent().getWarningRecommendBook(requireContext(), follower)

                WarningType.WARNING_DELETE_BOOK ->
                    WarningDialogContent().getWarningDeleteBook(requireContext())

                WarningType.WARNING_DELETE_FOLLOWER ->
                    WarningDialogContent().getWarningDeleteFollow(requireContext(), follower)

                WarningType.WARNING_UNFOLLOW ->
                    WarningDialogContent().getWarningUnfollow(requireContext(), follower)

                WarningType.WARNING_BOOK_DUPLICATE ->
                    WarningDialogContent().getWarningDuplicateBook(requireContext())
            }
        }
    }

    private fun initCancelBtnClickListener() {
        binding.btnWarningDialogCancel.setSingleOnClickListener {
            val warningType = arguments?.getSerializableCompat(WARNING_TYPE) as? WarningType
                ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            when (warningType as WarningType) {
                WarningType.WARNING_RECOMMEND, WarningType.WARNING_DELETE_BOOK, WarningType.WARNING_DELETE_FOLLOWER, WarningType.WARNING_UNFOLLOW -> {}
                WarningType.WARNING_BOOK_DUPLICATE -> {
                    arguments?.getParcelableCompat<ConfirmClickListener>(CONFIRM_ACTION)
                        ?.onConfirmClick()
                        ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
                }
            }
            dismiss()
        }
    }

    private fun initConfirmClickListener() {
        binding.btnWarningDialogConfirm.setSingleOnClickListener {
            val warningType = arguments?.getSerializableCompat(WARNING_TYPE) as? WarningType
                ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            when (warningType as WarningType) {
                WarningType.WARNING_RECOMMEND, WarningType.WARNING_DELETE_BOOK, WarningType.WARNING_DELETE_FOLLOWER, WarningType.WARNING_UNFOLLOW -> {
                    arguments?.getParcelableCompat<ConfirmClickListener>(CONFIRM_ACTION)
                        ?.onConfirmClick()
                        ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
                }

                WarningType.WARNING_BOOK_DUPLICATE -> {}
            }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DIALOG_WARNING = "warningDialog"
        const val WARNING_TYPE = "warningType"
        const val CONFIRM_ACTION = "confirmAction"
        const val FOLLOWER = "follower"
        const val DEFAULT = "default"
    }
}
