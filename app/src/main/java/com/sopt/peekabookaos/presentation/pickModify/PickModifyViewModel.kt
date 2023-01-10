package com.sopt.peekabookaos.presentation.pickModify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.PickModify
import com.sopt.peekabookaos.data.repository.ShelfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PickModifyViewModel @Inject constructor(
    private val shelfRepository: ShelfRepository
) : ViewModel() {
    private val _pickModifyData: MutableLiveData<List<PickModify>> = MutableLiveData()
    val pickModifyData: LiveData<List<PickModify>> = _pickModifyData

    private val _selectedItemList: MutableLiveData<LinkedHashSet<Int>> = MutableLiveData(
        linkedSetOf()
    )
    private val _selectState: MutableLiveData<Boolean> = MutableLiveData()
    val selectState: LiveData<Boolean> = _selectState
    private val _overListState: MutableLiveData<Boolean> = MutableLiveData()
    val overListState: LiveData<Boolean> = _overListState
    var preListState = _overListState.value

    init {
        getPick()
        _pickModifyData.value?.let { initSelectedItemList(it) }
    }

    fun updateSelectedItemState(item: PickModify) {
        preListState = _selectedItemList.value?.size!! >= 3
        if (item.pickIndex == 0 && _selectedItemList.value?.size!! < 3) {
            item.pickIndex = _selectedItemList.value?.size!! + 1
            _selectedItemList.value?.add(item.book.id)
            _selectState.value = true
        } else {
            _selectedItemList.value!!.remove(item.book.id)
            item.pickIndex = 0
            _selectState.value = false
            updateSelectedItemIndex()
        }
        _overListState.value = (_selectedItemList.value?.size!! >= 3 && preListState == true)
    }

    private fun initSelectedItemList(data: List<PickModify>) {
        for (item in data) {
            if (item.pickIndex != 0) {
                _selectedItemList.value?.add(item.book.id)
            }
        }
    }

    private fun updateSelectedItemIndex() {
        for (item in _pickModifyData.value!!) {
            if (_selectedItemList.value?.contains(item.book.id) == true) {
                item.pickIndex = getSelectedItemIndex(item)
            }
        }
    }

    private fun getSelectedItemIndex(item: PickModify): Int {
        val iterator = _selectedItemList.value!!.iterator()
        var count = 0
        while (iterator.hasNext()) {
            count++
            if (item.book.id == iterator.next()) {
                return count
            }
        }
        return count
    }

    private fun getPick() {
        viewModelScope.launch {
            shelfRepository.getPick()
                .onSuccess { response ->
                    _pickModifyData.value = response
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}
