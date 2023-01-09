package com.sopt.peekabookaos.presentation.search.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(User())
    val uiState = _uiState.asStateFlow()

    private val _isSearchStatus = MutableSharedFlow<Boolean>()
    val isSearchStatus = _isSearchStatus.asSharedFlow()

    private val _isFollowStatus = MutableSharedFlow<Boolean>()
    val isFollowStatus = _isFollowStatus.asSharedFlow()

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
