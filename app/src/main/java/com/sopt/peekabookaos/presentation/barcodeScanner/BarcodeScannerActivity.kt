package com.sopt.peekabookaos.presentation.barcodeScanner

import android.os.Bundle
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityBarcodeScannerBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BarcodeScannerActivity :
    BindingActivity<ActivityBarcodeScannerBinding>(R.layout.activity_barcode_scanner) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
