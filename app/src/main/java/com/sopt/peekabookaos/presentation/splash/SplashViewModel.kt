package com.sopt.peekabookaos.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.BuildConfig
import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.entity.Version
import com.sopt.peekabookaos.domain.entity.VersionDetail
import com.sopt.peekabookaos.domain.entity.VersionState
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
    private val _isForceUpdate = MutableLiveData(false)
    val isForceUpdate: LiveData<Boolean> = _isForceUpdate
    private lateinit var latestVersionDetail: VersionDetail
    private lateinit var appVersionDetail: VersionDetail

    init {
        getVersion()
    }

    fun getSplashState(): SplashState = getSplashStateUseCase()

    fun checkUpdateVersion(): VersionState {
        latestVersionDetail = spiltVersionToMajorMinor(latestVersion.androidForceVersion)
        appVersionDetail = spiltVersionToMajorMinor(BuildConfig.VERSION_NAME)
        return if (appVersionDetail.major != latestVersionDetail.major || appVersionDetail.minor != latestVersionDetail.minor) VersionState.OUTDATED
        else VersionState.LATEST
    }

    private fun spiltVersionToMajorMinor(versionName: String): VersionDetail {
        val versionSpiltList = versionName.split(".")
        val major = versionSpiltList[0]
        val minor = versionSpiltList[1]
        return VersionDetail(major, minor)
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
                    _isForceUpdate.value = true
                }
                .onFailure { throwable ->
                    _isForceUpdate.value = false
                    Timber.e("$throwable")
                }
        }
    }
}
