package com.sopt.peekabookaos.presentation.forceUpdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.entity.Version

class ForceUpdateViewModel : ViewModel() {
    private val _latestVersion: MutableLiveData<Version> = MutableLiveData()
    val latestVersion: LiveData<Version> = _latestVersion

    fun getLatestVersion(version: Version) {
        _latestVersion.value = version
    }
}
