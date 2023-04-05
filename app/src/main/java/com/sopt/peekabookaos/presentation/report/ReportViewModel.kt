package com.sopt.peekabookaos.presentation.report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportViewModel : ViewModel() {
    private val selectedReasonId = MutableLiveData(1)

    val reason = MutableLiveData("")

    fun setSelectedReasonId(id: Int) {
        selectedReasonId.value = id
    }
}
