package com.sopt.peekabookaos.presentation.pickModify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.PickModify

class PickModifyViewModel : ViewModel() {
    private val _pickModifyData: MutableLiveData<List<PickModify>> = MutableLiveData()
    val pickModifyData: LiveData<List<PickModify>> = _pickModifyData

    private val _selectedItemList: MutableLiveData<LinkedHashSet<Int>> = MutableLiveData(
        linkedSetOf()
    )
    private val _selectState: MutableLiveData<Boolean> = MutableLiveData()
    val selectState: LiveData<Boolean> = _selectState
    private val _overListState: MutableLiveData<Boolean> = MutableLiveData()
    val overListState: LiveData<Boolean> = _overListState

    init {
        initPickModifyData()
        _pickModifyData.value?.let { initSelectedItemList(it) }
    }

    fun updateSelectedItemState(item: PickModify) {
        if (_selectedItemList.value?.size!! >= 3) _overListState.value = true
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

    private fun getIinitSelectedItemPosition() {
    }

    private fun initPickModifyData() {
        _pickModifyData.value = listOf(
            PickModify(0, PickModify.Book(0, "https://image.yes24.com/goods/76106687/XL")),
            PickModify(2, PickModify.Book(1, "https://image.yes24.com/goods/114671132/XL")),
            PickModify(0, PickModify.Book(2, "https://image.yes24.com/goods/97255028/XL")),
            PickModify(
                1,
                PickModify.Book(
                    3,
                    "https://image.yes24.com/momo/TopCate215/MidCate002/21414510.jpg"
                )
            ),
            PickModify(0, PickModify.Book(4, "https://image.yes24.com/goods/72127217/XL")),
            PickModify(0, PickModify.Book(5, "https://image.yes24.com/goods/91159773/XL")),
            PickModify(
                0,
                PickModify.Book(
                    6,
                    "https://blog.kakaocdn.net/dn/66RoG/btqSGiU51MK/4NZO9mMJnAoFHmjXtSGiuK/img.png"
                )
            ),
            PickModify(3, PickModify.Book(7, "https://image.yes24.com/goods/72310907/XL"))
        )
    }
}
