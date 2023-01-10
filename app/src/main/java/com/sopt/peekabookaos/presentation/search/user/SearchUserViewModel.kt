package com.sopt.peekabookaos.presentation.search.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.User
import com.sopt.peekabookaos.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(User())
    val uiState = _uiState.asStateFlow()

    private val _isSearchStatus = MutableSharedFlow<Boolean>()
    val isSearchStatus = _isSearchStatus.asSharedFlow()

    private val isFollowStatus = MutableSharedFlow<Boolean>()

    val nickname = MutableStateFlow("")

    val isFollowed = MutableStateFlow(false)

    /* 서버 통신 시 제거 예정 */
    private val serverStatus = false

    fun searchBtnClickListener() {
        viewModelScope.launch {
            searchRepository.getSearchUser(nickname.value)
                .onSuccess { response ->
                    _uiState.value = response
                    isFollowed.value = response.isFollowed
                    _isSearchStatus.emit(true)
                    Timber.d("asdf success $response")
                }.onFailure { throwable ->
                    _isSearchStatus.emit(false)
                    Timber.e("asdf throwable $throwable")
                }
        }
    }

    fun followBtnClickListener() {
        /* 서버 통신 시 구현 예정*/
        viewModelScope.launch {
            if (isFollowed.value) {
                deleteFollow()
            } else {
                postFollow()
            }
        }
    }

    private fun deleteFollow() {
        viewModelScope.launch {
            if (serverStatus) {
                isFollowStatus.emit(true)
                isFollowed.value = false
            } else {
                isFollowStatus.emit(false)
            }
        }
    }

    private fun postFollow() {
        viewModelScope.launch {
            if (serverStatus) {
                isFollowStatus.emit(true)
                isFollowed.value = true
            } else {
                isFollowStatus.emit(false)
            }
        }
    }
}
