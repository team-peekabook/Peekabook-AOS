package com.sopt.peekabookaos.presentation.search.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.usecase.DeleteFollowUseCase
import com.sopt.peekabookaos.domain.usecase.GetSearchUserUseCase
import com.sopt.peekabookaos.domain.usecase.PostFollowUseCase
import com.sopt.peekabookaos.util.extensions.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val getSearchUserUseCase: GetSearchUserUseCase,
    private val deleteFollowUseCase: DeleteFollowUseCase,
    private val postFollowUseCase: PostFollowUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(User())
    val uiState = _uiState.asStateFlow()

    private val _searchState = MutableStateFlow<UiState>(UiState.IDLE)
    val searchState = _searchState.asStateFlow()

    private val _isFollowed = MutableStateFlow(false)
    val isFollowed = _isFollowed.asStateFlow()

    val nickname = MutableStateFlow("")

    fun searchBtnClickListener() {
        viewModelScope.launch {
            _searchState.emit(UiState.IDLE)
            getSearchUserUseCase(nickname.value)
                .onSuccess { response ->
                    _uiState.emit(response)
                    _isFollowed.emit(response.isFollowed)
                    _searchState.emit(UiState.SUCCESS)
                }.onFailure { throwable ->
                    _searchState.emit(UiState.ERROR)
                    Timber.e("$throwable")
                }
        }
    }

    fun followBtnClickListener() {
        if (_isFollowed.value) {
            deleteFollow()
        } else {
            postFollow()
        }
    }

    private fun deleteFollow() {
        viewModelScope.launch {
            deleteFollowUseCase(_uiState.value.id)
                .onSuccess {
                    _isFollowed.emit(false)
                }.onFailure { throwable ->
                    _isFollowed.emit(true)
                    Timber.e("$throwable")
                }
        }
    }

    private fun postFollow() {
        viewModelScope.launch {
            postFollowUseCase(_uiState.value.id)
                .onSuccess {
                    _isFollowed.emit(true)
                }.onFailure { throwable ->
                    _isFollowed.emit(false)
                    Timber.e("$throwable")
                }
        }
    }
}
