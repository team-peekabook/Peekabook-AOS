package com.sopt.peekabookaos.presentation.block

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.FriendList
import com.sopt.peekabookaos.domain.entity.SelfIntro
import com.sopt.peekabookaos.domain.usecase.DeleteBlockUseCase
import com.sopt.peekabookaos.domain.usecase.GetBlockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BlockViewModel @Inject constructor(
    private val getBlockUseCase: GetBlockUseCase,
    private val deleteBlockUseCase: DeleteBlockUseCase
) : ViewModel() {
    private val _blockData = MutableLiveData<List<FriendList>>()
    val blockData: LiveData<List<FriendList>> = _blockData

    private val _friendData: MutableLiveData<SelfIntro> = MutableLiveData()
    val friendData: LiveData<SelfIntro> = _friendData

    private val _isServerStatus = MutableLiveData(false)
    val isServerStatus: LiveData<Boolean> = _isServerStatus

    private val _friendId = MutableLiveData<Int>()
    val friendId: LiveData<Int> = _friendId

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

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

    fun deleteBlock(friendId: Int) {
        viewModelScope.launch {
            deleteBlockUseCase(friendId)
                .onSuccess { success ->
                    _isDeleted.value = success
                }.onFailure { throwable ->
                    _isDeleted.value = false
                    Timber.e("$throwable")
                }
        }
    }
}
