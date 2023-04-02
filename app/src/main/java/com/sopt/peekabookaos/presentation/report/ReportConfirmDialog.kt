package com.sopt.peekabookaos.presentation.report

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogReportConfirmBinding
import com.sopt.peekabookaos.presentation.main.MainActivity

class ReportConfirmDialog : DialogFragment() {
    private var _binding: DialogReportConfirmBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_report_confirm, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initReportBtnClickListener()
    }

    private fun initLayout() {
        val ratio = 0.89
        val layoutParams = requireNotNull(dialog).window!!.attributes
        layoutParams.width = (resources.displayMetrics.widthPixels * ratio).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        requireNotNull(dialog).window!!.attributes = layoutParams
        isCancelable = true
    }

    private fun initReportBtnClickListener() {
        binding.btnDialogReportHome.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ReportDialogFragment"
    }
}
