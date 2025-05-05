package com.sopt.peekabookaos.presentation.barcodeScanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogBarcodeErrorBinding
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener

class BarcodeErrorDialog : DialogFragment() {
    private var _binding: DialogBarcodeErrorBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val barcodeViewModel by activityViewModels<BarcodeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_barcode_error, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = barcodeViewModel
        initLayout()
        initCloseBtnClickListener()
        initSearchBtnClickListener()
    }

    private fun initLayout() {
        val ratio = 0.89
        val layoutParams = requireNotNull(dialog).window!!.attributes
        layoutParams.width = (resources.displayMetrics.widthPixels * ratio).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        requireNotNull(dialog).window!!.attributes = layoutParams
        isCancelable = true
    }

    private fun initCloseBtnClickListener() {
        binding.btnBarcodeErrorClose.setSingleOnClickListener {
            barcodeViewModel.updateServerState()
            dismiss()
        }
    }

    private fun initSearchBtnClickListener() {
        binding.btnBarcodeErrorSearch.setSingleOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "BarcodeErrorDialogFragment"
    }
}
