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
        initReportBtnClickListener()
        /*binding.rgReportRadiogroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_report_reason_inadequate ->
            }
        }*/ // 다른 항목 선택하면 각자 다른 데이터가 전달됐음 좋겠는데 잘모르겠음
        binding.rgReportRadiogroup.check(R.id.rb_report_reason_inadequate)
    }

    private fun initReportBtnClickListener() {
        binding.btnReportReport.setSingleOnClickListener {
            ReportConfirmDialog().show(supportFragmentManager, TAG)
        }
    }
}
