package com.sopt.peekabookaos.presentation.forceUpdate

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.entity.Version

class ForceUpdateViewModel : ViewModel() {
    lateinit var latestVersion: Version

    fun getLatestVersion(version: Version) {
        latestVersion = version
    }
}
