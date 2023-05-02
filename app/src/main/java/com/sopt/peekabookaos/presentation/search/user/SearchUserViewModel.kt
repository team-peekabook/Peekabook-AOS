package com.sopt.peekabookaos.presentation.search.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.usecase.DeleteBlockUseCase
import com.sopt.peekabookaos.domain.usecase.DeleteFollowUseCase
import com.sopt.peekabookaos.domain.usecase.GetSearchUserUseCase
import com.sopt.peekabookaos.domain.usecase.PostFollowUseCase
import com.sopt.peekabookaos.util.UiEvent
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
    private val getSearchUserUseCase: GetSearchUserUseCase,
    private val deleteFollowUseCase: DeleteFollowUseCase,
    private val postFollowUseCase: PostFollowUseCase,
    private val deleteBlockUseCase: DeleteBlockUseCase
) : ViewModel() {
    private val _userInfo = MutableStateFlow(User())
    val userInfo = _userInfo.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val nickname = MutableStateFlow("")

    fun searchOnClick() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.IDLE)
            getSearchUserUseCase(nickname.value)
                .onSuccess { response ->
                    _userInfo.emit(response)
                    _uiEvent.emit(UiEvent.SUCCESS)
                }.onFailure { throwable ->
                    _uiEvent.emit(UiEvent.ERROR)
                    Timber.e("$throwable")
                }
        }
    }

    fun followOnClick() {
        if (_userInfo.value.isBlocked) {
            blockUser()
        } else {
            if (_userInfo.value.isFollowed) {
                deleteFollow()
            } else {
                postFollow()
            }
        }
    }

    private fun blockUser() {
        viewModelScope.launch {
            deleteBlockUseCase(_userInfo.value.id)
                .onSuccess { isBlocked ->
                    _userInfo.emit(_userInfo.value.copy(isBlocked = !isBlocked))
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    private fun deleteFollow() {
        viewModelScope.launch {
            deleteFollowUseCase(_userInfo.value.id)
                .onSuccess { isFollowed ->
                    _userInfo.emit(_userInfo.value.copy(isFollowed = !isFollowed))
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    private fun postFollow() {
        viewModelScope.launch {
            postFollowUseCase(_userInfo.value.id)
                .onSuccess { isFollowed ->
                    _userInfo.emit(_userInfo.value.copy(isFollowed = isFollowed))
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}
