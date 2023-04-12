package com.sopt.peekabookaos.presentation.search.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.usecase.GetBookToTitleUseCase
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

    private val _isSearch = MutableSharedFlow<Boolean>()
    val isSearch = _isSearch.asSharedFlow()

    val bookTitle = MutableStateFlow("")

    fun searchBtnClickListener() {
        viewModelScope.launch {
            getBookToTitleUseCase(bookTitle.value)
                .onSuccess { response ->
                    if (response.isEmpty()) {
                        _isSearch.emit(false)
                    } else {
                        _uiState.value = _uiState.value.copy(book = response)
                        _isSearch.emit(true)
                    }
                }.onFailure { throwable ->
                    _isSearch.emit(false)
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
