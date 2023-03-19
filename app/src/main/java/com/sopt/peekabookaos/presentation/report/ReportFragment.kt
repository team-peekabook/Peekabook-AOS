package com.sopt.peekabookaos.presentation.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentReportBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.selects.select

@AndroidEntryPoint
class ReportFragment : BindingFragment<FragmentReportBinding>(R.layout.fragment_report) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.rgReportRadiogroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_report_reason_inadequate ->
            }
        }
    }
}