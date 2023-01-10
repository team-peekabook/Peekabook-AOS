package com.sopt.peekabookaos.presentation.createUpdateBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.repository.CreateUpdateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateUpdateBookViewModel @Inject constructor(
    private val createUpdateRepository: CreateUpdateRepository
) : ViewModel() {
    private val _bookData = MutableLiveData<Book>()
    val bookData: LiveData<Book> = _bookData

    private val _isUpdateView = MutableLiveData<Boolean>()
    val isUpdateView: LiveData<Boolean> = _isUpdateView

    private val createUpdateRequest = MutableLiveData<CreateBookRequest>()

    private val _isServerStatus = MutableLiveData<Boolean>()
    val isServerStatus: LiveData<Boolean> = _isServerStatus

    val comment = MutableLiveData("")

    val memo = MutableLiveData("")

    fun initIsUpdateView(update: Boolean) {
        _isUpdateView.value = update
    }

    fun initSaveClickListener() {
        if (_isUpdateView.value == true) {
            Timber.d("책 수정 서버 통신")
        } else {
            postCreateBook()
        }
    }

    private fun postCreateBook() {
        initCreateBookRequest(
            bookImage = _bookData.value!!.bookImage,
            bookTitle = _bookData.value!!.bookTitle,
            author = _bookData.value!!.author,
            description = comment.value,
            memo = memo.value
        )
        viewModelScope.launch {
            createUpdateRepository.postCreateBook(createUpdateRequest.value!!)
                .onSuccess {
                    _isServerStatus.value = true
                }.onFailure {
                    _isServerStatus.value = false
                }
        }
    }

    private fun initCreateBookRequest(
        bookImage: String,
        bookTitle: String,
        author: String,
        description: String?,
        memo: String?
    ) {
        createUpdateRequest.value =
            CreateBookRequest(bookImage, bookTitle, author, description, memo)
    }

    fun initCreateUpdateBookData(bookData: Book) {
        _bookData.value = bookData
//        comment.value = _bookData.value?.description
//        memo.value = _bookData.value?.memo
    }
}
