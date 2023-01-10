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
        /* 서버 통신 시 구현 예정*/
        viewModelScope.launch {
            if (serverStatus) {
                _isSearchStatus.emit(true)
                _uiState.value = dummy
                isFollowed.value = dummy.isFollowed
            } else {
                _isSearchStatus.emit(false)
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

    companion object {
        private val dummy = User(
            id = 1,
            nickname = "이빵주",
            profileImage = "http://image.yes24.com/goods/90365124/XL",
            intro = "어쩔티비",
            isFollowed = true
        )
    }
}
