package com.sopt.peekabookaos.presentation.block

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.User
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
    private val _blockData = MutableLiveData<List<User>>()
    val blockData: LiveData<List<User>> = _blockData

    private val _friendData: MutableLiveData<User> = MutableLiveData()
    val friendData: LiveData<User> = _friendData

    val nickname = MutableLiveData<String>()

    private val _friendId = MutableLiveData<Int>()
    val friendId: LiveData<Int> = _friendId

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    init {
        getBlockList()
    }

    fun getBlockList() {
        viewModelScope.launch {
            getBlockUseCase()
                .onSuccess { response ->
                    _blockData.value = response
                }
                .onFailure { throwable ->
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
