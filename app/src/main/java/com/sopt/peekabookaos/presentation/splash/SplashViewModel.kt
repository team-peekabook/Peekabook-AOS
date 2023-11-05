package com.sopt.peekabookaos.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.entity.Version
import com.sopt.peekabookaos.domain.usecase.GetSplashStateUseCase
import com.sopt.peekabookaos.domain.usecase.GetVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSplashStateUseCase: GetSplashStateUseCase,
    private val getVersionUseCase: GetVersionUseCase
) : ViewModel() {
    var latestVersion = Version()
    private val _isForceUpdateStatus = MutableLiveData(false)
    val isForceUpdateStatus: LiveData<Boolean> = _isForceUpdateStatus
    lateinit var majorVersion: String
    lateinit var minorVersion: String

    init {
        getVersion()
    }

    fun getSplashState(): SplashState = getSplashStateUseCase()

    fun getSplitVersion() = run {
        val versionSpiltList = latestVersion.androidForceVersion.split(".")
        majorVersion = versionSpiltList[0]
        minorVersion = versionSpiltList[1]
    }

    private fun getVersion() {
        viewModelScope.launch {
            getVersionUseCase()
                .onSuccess { response ->
                    latestVersion = Version(
                        response.imageUrl,
                        response.androidForceVersion,
                        response.versionText
                    )
                    _isForceUpdateStatus.value = true
                }
                .onFailure { throwable ->
                    _isForceUpdateStatus.value = false
                    Timber.e("$throwable")
                }
        }
    }
}
