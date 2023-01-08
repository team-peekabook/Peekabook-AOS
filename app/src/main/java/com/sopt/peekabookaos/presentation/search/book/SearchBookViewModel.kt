package com.sopt.peekabookaos.presentation.search.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.util.extensions.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchBookViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Book>>>(UiState.IDLE)
    val uiState: StateFlow<UiState<List<Book>>> = _uiState.asStateFlow()

    /* 서버 통신 시 제거 예정 */
    private val serverStatus = true

    val bookTitle = MutableStateFlow("")

    fun searchBtnClickListener() {
        /* 서버 통신 시 구현 예정*/
        viewModelScope.launch {
            _uiState.emit(UiState.IDLE)
            if (serverStatus) {
                _uiState.emit(UiState.Success(dummy))
            } else {
                _uiState.emit(UiState.Error(Throwable()))
            }
        }
    }

    companion object {
        private val dummy = listOf(
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회",
                description = "",
                memo = ""
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 가을",
                author = "김신회",
                description = "",
                memo = ""
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회",
                description = "",
                memo = ""
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 가을",
                author = "김신회",
                description = "",
                memo = ""
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회",
                description = "",
                memo = ""
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회",
                description = "",
                memo = ""
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 가을",
                author = "김신회",
                description = "",
                memo = ""
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회",
                description = "",
                memo = ""
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 가을",
                author = "김신회",
                description = "",
                memo = ""
            )
        )
    }
}
