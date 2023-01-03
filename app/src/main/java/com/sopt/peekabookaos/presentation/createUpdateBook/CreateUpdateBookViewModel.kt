package com.sopt.peekabookaos.presentation.createUpdateBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.CreateUpdateBook
import timber.log.Timber

class CreateUpdateBookViewModel : ViewModel() {
    private val _bookData = MutableLiveData<CreateUpdateBook>()
    val bookData: LiveData<CreateUpdateBook> = _bookData

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

    fun initCreateUpdateBookData(bookData: CreateUpdateBook) {
        _bookData.value = bookData
        comment.value = _bookData.value?.description
        memo.value = _bookData.value?.memo
    }
}
