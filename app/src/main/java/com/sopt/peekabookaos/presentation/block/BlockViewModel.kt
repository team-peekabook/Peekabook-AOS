package com.sopt.peekabookaos.presentation.block

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.FriendList
import com.sopt.peekabookaos.domain.entity.SelfIntro
import com.sopt.peekabookaos.domain.usecase.GetBlockUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class BlockViewModel @Inject constructor(
    private val getBlockUseCase: GetBlockUseCase
) : ViewModel() {
    private val _blockData = MutableLiveData<List<FriendList>>()
    val blockData: LiveData<List<FriendList>> = _blockData

    private val _friendData: MutableLiveData<SelfIntro> = MutableLiveData()
    val friendData: LiveData<SelfIntro> = _friendData

    private val _isServerStatus = MutableLiveData(false)
    val isServerStatus: LiveData<Boolean> = _isServerStatus

    init {
        getBlock()
    }

    private fun getBlock() {
        viewModelScope.launch {
            getBlockUseCase()
                .onSuccess { response ->
                    _blockData.value = response
                    _isServerStatus.value = true
                }
                .onFailure { throwable ->
                    _isServerStatus.value = false
                    Timber.e("$throwable")
                }
        }
    }
    fun deleteBlock() {
        // TODO("차단 api")
    }
}
