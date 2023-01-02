package com.sopt.peekabookaos.presentation.createUpdateBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

    }
}
