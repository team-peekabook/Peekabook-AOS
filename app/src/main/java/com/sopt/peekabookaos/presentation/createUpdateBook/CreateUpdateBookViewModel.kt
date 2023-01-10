package com.sopt.peekabookaos.presentation.createUpdateBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.repository.CreateUpdateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber

class CreateUpdateBookViewModel : ViewModel() {
@HiltViewModel
class CreateUpdateBookViewModel @Inject constructor(
    private val createUpdateRepository: CreateUpdateRepository
) : ViewModel() {
    private val _bookData = MutableLiveData<Book>()
    val bookData: LiveData<Book> = _bookData

    private val _isUpdateView = MutableLiveData<Boolean>()
    val isUpdateView: LiveData<Boolean> = _isUpdateView

    val comment = MutableLiveData("")

    val memo = MutableLiveData("")

    fun initIsUpdateView(update: Boolean) {
        _isUpdateView.value = update
    }

    fun initSaveClickListener() {
        if (_isUpdateView.value == true) {
            Timber.d("책 수정 서버 통신")
        } else {
            Timber.d("책 등록 서버 통신")
        }
    }

    fun initCreateUpdateBookData(bookData: Book) {
        _bookData.value = bookData
        comment.value = _bookData.value?.description
        memo.value = _bookData.value?.memo
    }
}
