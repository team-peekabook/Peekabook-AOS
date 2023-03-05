package com.sopt.peekabookaos.presentation.updateBook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.BookComment
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.repository.CreateUpdateRepository
import com.sopt.peekabookaos.domain.entity.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UpdateBookViewModel @Inject constructor(
    private val createUpdateRepository: CreateUpdateRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateUpdateUiState())
    val uiState = _uiState.asStateFlow()

    private val _isPost = MutableSharedFlow<Boolean>()
    val isPost = _isPost.asSharedFlow()

    private val _isPatch = MutableSharedFlow<Boolean>()
    val isPatch = _isPatch.asSharedFlow()

    val description = MutableStateFlow<String?>("")

    val memo = MutableStateFlow<String?>("")

    fun initSaveClickListener() {
        if (_uiState.value.isUpdateView) {
            patchBook()
        } else {
            postCreateBook()
        }
    }

    private fun patchBook() {
        viewModelScope.launch {
            createUpdateRepository.patchBook(
                _uiState.value.bookData.id,
                BookComment(
                    description = description.value,
                    memo = memo.value
                )
            ).onSuccess {
                _isPatch.emit(true)
            }.onFailure { throwable ->
                Timber.e("$throwable")
            }
        }
    }

    private fun postCreateBook() {
        viewModelScope.launch {
            createUpdateRepository.postCreateBook(
                CreateBookRequest(
                    bookImage = _uiState.value.bookData.bookImage,
                    bookTitle = _uiState.value.bookData.bookTitle,
                    author = _uiState.value.bookData.author,
                    description = description.value,
                    memo = memo.value
                )
            )
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        bookData = Book(id = response.bookId)
                    )
                    _isPost.emit(true)
                }.onFailure { throwable ->
                    _isPost.emit(false)
                    Timber.e("$throwable")
                }
        }
    }

    fun initUiState(bookData: Book, bookComment: BookComment, update: Boolean) {
        _uiState.value = CreateUpdateUiState().copy(
            bookData = bookData,
            bookComment = bookComment,
            isUpdateView = update
        )
        description.value = _uiState.value.bookComment.description
        memo.value = _uiState.value.bookComment.memo
    }

    data class CreateUpdateUiState(
        val bookData: Book = Book(),
        val bookComment: BookComment = BookComment("", ""),
        val isUpdateView: Boolean = false
    )
}
