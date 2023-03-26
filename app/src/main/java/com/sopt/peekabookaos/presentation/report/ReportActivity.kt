package com.sopt.peekabookaos.presentation.report

import android.os.Bundle
import android.util.Log
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
        binding.rgReportRadiogroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_report_reason_inadequate -> Log.d("1", "부적절한 게시물")
                R.id.rb_report_reason_curse -> Log.d("2", "욕설 및 비하 발언")
                R.id.rb_report_reason_promote -> Log.d("3", "홍보성 컨텐츠")
                R.id.rb_report_reason_nickname -> Log.d("4", "닉네임 신고")
                R.id.rb_report_reason_etc -> Log.d("5", "기타")
            }
        }
        binding.rgReportRadiogroup.check(R.id.rb_report_reason_inadequate)
    }

    private fun initReportBtnClickListener() {
        binding.btnReportReport.setSingleOnClickListener {
            ReportConfirmDialog().show(supportFragmentManager, TAG)
        }
    }
}
