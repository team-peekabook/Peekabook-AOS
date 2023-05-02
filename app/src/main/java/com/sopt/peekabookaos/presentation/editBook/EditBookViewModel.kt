package com.sopt.peekabookaos.presentation.editBook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.entity.BookComment
import com.sopt.peekabookaos.domain.usecase.PatchEditBookUseCase
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
class EditBookViewModel @Inject constructor(
    private val patchEditBookUseCase: PatchEditBookUseCase
) : ViewModel() {
    private val _bookInfo = MutableStateFlow(Book())
    val bookInfo = _bookInfo.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val comment = MutableStateFlow("")

    val memo = MutableStateFlow("")

    fun patchEditBook() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.IDLE)
            patchEditBookUseCase(
                bookId = _bookInfo.value.id,
                description = comment.value,
                memo = memo.value
            ).onSuccess {
                _uiEvent.emit(UiEvent.SUCCESS)
            }.onFailure { throwable ->
                _uiEvent.emit(UiEvent.ERROR)
                Timber.e("$throwable")
            }
        }
    }

    fun setPreviousBook(bookInfo: Book, bookComment: BookComment) {
        _bookInfo.value = bookInfo
        comment.value = bookComment.description ?: ""
        memo.value = bookComment.memo ?: ""
    }
}
