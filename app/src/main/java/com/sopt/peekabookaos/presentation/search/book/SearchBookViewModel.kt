package com.sopt.peekabookaos.presentation.search.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.usecase.GetBookToTitleUseCase
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
class SearchBookViewModel @Inject constructor(
    private val getBookToTitleUseCase: GetBookToTitleUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchBookUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val bookTitle = MutableStateFlow("")

    fun searchOnClick() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.IDLE)
            getBookToTitleUseCase(bookTitle.value)
                .onSuccess { response ->
                    if (response.isEmpty()) {
                        _uiEvent.emit(UiEvent.ERROR)
                    } else {
                        _uiState.value = _uiState.value.copy(book = response)
                        _uiEvent.emit(UiEvent.SUCCESS)
                    }
                }.onFailure { throwable ->
                    _uiEvent.emit(UiEvent.ERROR)
                    Timber.e("$throwable")
                }
        }
    }

    fun updateUiState(friendInfo: User, isCreateView: Boolean) {
        _uiState.value = _uiState.value.copy(
            friendInfo = friendInfo,
            isCreateView = isCreateView
        )
    }

    data class SearchBookUiState(
        val book: List<Book> = emptyList(),
        val friendInfo: User = User(),
        val isCreateView: Boolean = false
    )
}
