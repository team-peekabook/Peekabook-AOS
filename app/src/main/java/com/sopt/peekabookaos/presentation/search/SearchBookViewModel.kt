package com.sopt.peekabookaos.presentation.search

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.util.extensions.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchBookViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Book>>>(UiState.IDLE)
    val uiState: StateFlow<UiState<List<Book>>> = _uiState.asStateFlow()

    /* 서버 통신 시 제거 예정 */
    private val serverStatus = true

    val bookTitle = MutableStateFlow("")

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
