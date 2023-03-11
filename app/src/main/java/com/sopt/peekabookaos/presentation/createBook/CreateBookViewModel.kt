package com.sopt.peekabookaos.presentation.createBook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.BookComment
import com.sopt.peekabookaos.domain.usecase.PostCreateBookUseCase
import com.sopt.peekabookaos.util.extensions.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateBookViewModel @Inject constructor(
    private val postCreateBookUseCase: PostCreateBookUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateUpdateUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val comment = MutableStateFlow<String?>("")

    val memo = MutableStateFlow<String?>("")

    fun postCreateBook() {
        viewModelScope.launch {
            postCreateBookUseCase(
                bookImage = _uiState.value.bookInfo.bookImage,
                bookTitle = _uiState.value.bookInfo.bookTitle,
                author = _uiState.value.bookInfo.author,
                description = comment.value,
                memo = memo.value
            ).onSuccess { response ->
                _uiState.value = _uiState.value.copy(
                    bookInfo = Book(id = response.id)
                )
                _uiEvent.emit(UiEvent.SUCCESS)
            }.onFailure { throwable ->
                _uiEvent.emit(UiEvent.ERROR)
                Timber.e("$throwable")
            }
        }
    }

    fun initBookInfo(bookInfo: Book) {
        _uiState.value = _uiState.value.copy(bookInfo = bookInfo)
    }

    data class CreateUpdateUiState(
        val bookInfo: Book = Book(),
        val bookComment: BookComment = BookComment()
    )
}
