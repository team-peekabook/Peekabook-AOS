package com.sopt.peekabookaos.presentation.report

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityReportBinding
import com.sopt.peekabookaos.presentation.report.ReportConfirmDialog.Companion.TAG
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : BindingActivity<ActivityReportBinding>(R.layout.activity_report) {
    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = reportViewModel

        initReportBtnClickListener()
        binding.rgReportRadiogroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_report_reason_inadequate -> reportViewModel.setSelectedReasonId(1)
                R.id.rb_report_reason_curse -> reportViewModel.setSelectedReasonId(2)
                R.id.rb_report_reason_promote -> reportViewModel.setSelectedReasonId(3)
                R.id.rb_report_reason_nickname -> reportViewModel.setSelectedReasonId(4)
                R.id.rb_report_reason_etc -> reportViewModel.setSelectedReasonId(5)
            }
        }
    }

    private fun initReportBtnClickListener() {
        binding.btnReportReport.setSingleOnClickListener {
            ReportConfirmDialog().show(supportFragmentManager, TAG)
        }
    }
}
