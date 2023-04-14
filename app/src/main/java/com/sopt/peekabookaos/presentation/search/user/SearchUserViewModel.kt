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
    private val _uiState = MutableStateFlow(User())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val nickname = MutableStateFlow("")

    fun searchBtnClickListener() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.IDLE)
            getSearchUserUseCase(nickname.value)
                .onSuccess { response ->
                    _uiState.value = response
                    _uiEvent.emit(UiEvent.SUCCESS)
                }.onFailure { throwable ->
                    _uiEvent.emit(UiEvent.ERROR)
                    Timber.e("$throwable")
                }
        }
    }

    fun followBtnClickListener() {
        if (_uiState.value.isBlocked) {
            blockUser()
        } else {
            if (_uiState.value.isFollowed) {
                deleteFollow()
            } else {
                postFollow()
            }
        }
    }

    private fun blockUser() {
        viewModelScope.launch {
            deleteBlockUseCase(_uiState.value.id)
                .onSuccess { isBlocked ->
                    _uiState.value = _uiState.value.copy(isBlocked = !isBlocked)
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    private fun deleteFollow() {
        viewModelScope.launch {
            deleteFollowUseCase(_uiState.value.id)
                .onSuccess { isFollowed ->
                    _uiState.value = _uiState.value.copy(isFollowed = !isFollowed)
                }.onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(isFollowed = true)
                    Timber.e("$throwable")
                }
        }
    }

    private fun postFollow() {
        viewModelScope.launch {
            postFollowUseCase(_uiState.value.id)
                .onSuccess { isFollowed ->
                    _uiState.value = _uiState.value.copy(isFollowed = isFollowed)
                }.onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(isFollowed = false)
                    Timber.e("$throwable")
                }
        }
    }
}
