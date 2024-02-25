package com.sopt.peekabookaos.presentation.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogRecommendDeleteBinding
import com.sopt.peekabookaos.util.UiEvent
import com.sopt.peekabookaos.util.extensions.getSerializableCompat
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import timber.log.Timber

class RecommendDeleteDialog : DialogFragment() {
    private var _binding: DialogRecommendDeleteBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val recommendViewModel by activityViewModels<RecommendViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRecommendDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        initLayout()
        initWarningDialogContent()
        initConfirmBtnClickListener()
        initCancelBtnClickListener()
        collectUiEvent()
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            recommendViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.IDLE -> {}
                    UiEvent.SUCCESS -> {
                        recommendViewModel.getRecommend()
                        dismiss()
                    }

                    UiEvent.ERROR -> {
                        dismiss()
                    }
                }
            }
        }
    }

    private fun initLayout() {
        val ratio = 0.89
        val layoutParams = requireNotNull(dialog).window!!.attributes
        layoutParams.width = (resources.displayMetrics.widthPixels * ratio).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        requireNotNull(dialog).window!!.attributes = layoutParams
    }

    private fun initWarningDialogContent() {
        val recommendType = arguments?.getSerializableCompat(RECOMMEND_TYPE) as? RecommendType
            ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
        with(binding) {
            type = when (recommendType as RecommendType) {
                RecommendType.RECOMMENDED ->
                    RecommendDeleteDialogContent().getRecommended(requireContext())

                RecommendType.RECOMMENDING ->
                    RecommendDeleteDialogContent().getRecommending(requireContext())
            }
        }
    }

    private fun initConfirmBtnClickListener() {
        binding.btnRecommendDeleteDialogConfirm.setOnClickListener {
            recommendViewModel.deleteRecommend()
        }
    }

    private fun initCancelBtnClickListener() {
        binding.btnRecommendDeleteDialogCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DIALOG_TYPE = "recommendDeleteDialog"
        const val RECOMMEND_TYPE = "recommendType"
    }
}
