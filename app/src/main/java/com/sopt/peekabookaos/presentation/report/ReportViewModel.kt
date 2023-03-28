package com.sopt.peekabookaos.presentation.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ReportViewModel : ViewModel() {
    private val _selectedReasonId = MutableLiveData(1)
    val selectedReasonId: LiveData<Int> = _selectedReasonId
    val reason = MutableLiveData("")
    fun setSelectedReasonId(id: Int) {
        _selectedReasonId.value = id
        Timber.d("${selectedReasonId.value}")
    }
}
