package com.sopt.peekabookaos.presentation.createUpdateBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class CreateUpdateBookViewModel : ViewModel() {
    private val _createUpdateBookData = MutableLiveData<CreateUpdateBook>()
    val createUpdateBookData: LiveData<CreateUpdateBook> = _createUpdateBookData

    private val _isUpdate = MutableLiveData<Boolean>()
    val isUpdate: LiveData<Boolean> = _isUpdate

    val comment = MutableLiveData("")

    val memo = MutableLiveData("")

    fun initIsUpdate(update: Boolean) {
        _isUpdate.value = update
    }

    fun initSaveClickListener() {
        if (_isUpdate.value == true) {
            Timber.d("책 수정 서버 통신")
        } else {
            Timber.d("책 등록 서버 통신")
        }
    }

    }
}
