package com.sopt.peekabookaos.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogWarningBinding
import com.sopt.peekabookaos.util.extensions.initLayout
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

    private fun initWarningDialogContent() {
        val warningType = arguments?.get(WARNING_TYPE)
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
            }
        }
    }

    }
}
