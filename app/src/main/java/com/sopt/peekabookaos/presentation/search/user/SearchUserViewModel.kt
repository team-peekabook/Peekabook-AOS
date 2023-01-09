package com.sopt.peekabookaos.presentation.search.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUserUiState())
    val uiState = _uiState.asStateFlow()

    private val _isServerStatus = MutableSharedFlow<Boolean>()
    val isServerStatus = _isServerStatus.asSharedFlow()

    val nickname = MutableStateFlow("")

    /* 서버 통신 시 제거 예정 */
    private val serverStatus = false

    fun searchBtnClickListener() {
        /* 서버 통신 시 구현 예정*/
        viewModelScope.launch {
            if (serverStatus) {
                _isServerStatus.emit(true)
                _uiState.value = _uiState.value.copy(
                    id = dummy.id,
                    nickname = dummy.nickname,
                    profileImage = dummy.profileImage,
                    intro = dummy.intro
                )
            } else {
                _isServerStatus.emit(false)
                Timber.e("error.")
            }
        }
    }

}
