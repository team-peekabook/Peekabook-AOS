package com.sopt.peekabookaos.presentation.report

import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityReportBinding
import com.sopt.peekabookaos.presentation.detail.DetailActivity.Companion.DEFAULT
import com.sopt.peekabookaos.presentation.report.ReportDialog.Companion.TAG
import com.sopt.peekabookaos.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : BindingActivity<ActivityReportBinding>(R.layout.activity_report) {
    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = reportViewModel
        initFriendId()
        initReportRadioClickListener()
        initIsReportObserve()
    }

    private fun initFriendId() {
        reportViewModel.initFriendId(intent.getIntExtra(FRIEND_ID, DEFAULT))
    }

    private fun initReportRadioClickListener() {
        binding.rgReportRadiogroup.setOnCheckedChangeListener { _, checkedId ->
            setSelectedReasonId(checkedId)
        }
    }

    private fun setSelectedReasonId(checkedId: Int) {
        when (checkedId) {
            R.id.rb_report_reason_inadequate -> reportViewModel.setSelectedReasonId(1)
            R.id.rb_report_reason_curse -> reportViewModel.setSelectedReasonId(2)
            R.id.rb_report_reason_promote -> reportViewModel.setSelectedReasonId(3)
            R.id.rb_report_reason_nickname -> reportViewModel.setSelectedReasonId(4)
            R.id.rb_report_reason_etc -> reportViewModel.setSelectedReasonId(5)
        }
    }

    private fun initIsReportObserve() {
        reportViewModel.isReport.observe(this) { success ->
            if (success) {
                ReportDialog().show(supportFragmentManager, TAG)
            }
        }
    }

    companion object {
        const val FRIEND_ID = "friendId"
    }
}
