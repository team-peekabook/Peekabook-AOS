package com.sopt.peekabookaos.presentation.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.usecase.PostReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val postReportUseCase: PostReportUseCase
) : ViewModel() {
    private val selectedReasonId = MutableLiveData(1)

    val reason = MutableLiveData("")

    private val _friendId = MutableLiveData<Int>()
    val friendId: LiveData<Int> = _friendId

    fun setSelectedReasonId(id: Int) {
        selectedReasonId.value = id
    }

    fun initFriendId(id: Int) {
        _friendId.value = id
    }

    private val _isReport = MutableLiveData<Boolean>()
    val isReport: LiveData<Boolean> = _isReport

    fun postReport() {
        viewModelScope.launch {
            postReportUseCase(
                reasonIndex = requireNotNull(selectedReasonId.value),
                etc = reason.value,
                requireNotNull(_friendId.value)
            ).onSuccess {
                _isReport.value = true
            }.onFailure { throwable ->
                _isReport.value = false
                Timber.e("$throwable")
            }
        }
    }
}
