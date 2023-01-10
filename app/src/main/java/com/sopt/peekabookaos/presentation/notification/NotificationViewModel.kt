package com.sopt.peekabookaos.presentation.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Notification
import com.sopt.peekabookaos.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) :
    ViewModel() {
    private val _notificationData: MutableLiveData<List<Notification>> = MutableLiveData()
    val notificationData: LiveData<List<Notification>> = _notificationData

    private val _isServerStatus = MutableLiveData<Boolean>()
    val isServerStatus: LiveData<Boolean> = _isServerStatus

    init {
        getAlarm()
    }

    private fun getAlarm() {
        viewModelScope.launch {
            notificationRepository.getAlarm()
                .onSuccess { response ->
                    _notificationData.value = response
                    _isServerStatus.value = true
                }
                .onFailure { throwable ->
                    _isServerStatus.value = false
                    Timber.e("$throwable")
                }
        }
    }
}
