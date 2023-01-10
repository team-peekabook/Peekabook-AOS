package com.sopt.peekabookaos.presentation.search.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.User
import com.sopt.peekabookaos.data.repository.SearchRepository
import com.sopt.peekabookaos.util.extensions.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _searchState = MutableStateFlow<UiState>(UiState.IDLE)
    val searchState = _searchState.asStateFlow()

    private val isFollowStatus = MutableSharedFlow<Boolean>()

    val nickname = MutableStateFlow("")

    val isFollowed = MutableStateFlow(false)

    /* 서버 통신 시 제거 예정 */
    private val serverStatus = false

    fun searchBtnClickListener() {
        viewModelScope.launch {
            _searchState.emit(UiState.IDLE)
            searchRepository.getSearchUser(nickname.value)
                .onSuccess { response ->
                    _uiState.value = response
                    isFollowed.value = response.isFollowed
                    _searchState.emit(UiState.SUCCESS)
                    Timber.d("asdf success $response")
                }.onFailure { throwable ->
                    _searchState.emit(UiState.ERROR)
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
