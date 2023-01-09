package com.sopt.peekabookaos.presentation.pickModify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.PickModify

class PickModifyViewModel : ViewModel() {
    private val _pickShelfData: MutableLiveData<List<PickModify>> = MutableLiveData()
    val pickShelfData: LiveData<List<PickModify>> = _pickShelfData

    private val _selectedItemIdData: MutableLiveData<LinkedHashSet<Int>> = MutableLiveData(
        linkedSetOf()
    )
    private val selectedItemData: LiveData<LinkedHashSet<Int>> = _selectedItemIdData
//
//    private val _itemSelectState: MutableLiveData<Boolean> = MutableLiveData()
//    val itemSelectState: LiveData<Boolean> = _itemSelectState
//
//    var position: Int = -1

    init {
        initShelfData()
    }

//    fun updateSelectedItem(pos: Int, item: Shelf) {
//        position = pos
//        if (_selectedItemIdData.value?.contains(item.bookId) == true) { // bookId에 이미 존재하면 제거
//            _itemSelectState.value = false
//        } else { // 존재하지 않고
//            _itemSelectState.value = _selectedItemIdData.value!!.size <= 2
//        }
//    }
//
//    fun removeItem(item: Shelf) {
//        _selectedItemIdData.value!!.remove(item.bookId)
//    }
//
//    fun addItem(item: Shelf) {
//        _selectedItemIdData.value?.add(item.bookId)
//    }
//
//    fun getSelectedItemIndex(position: Int, item: Shelf): Int { // 책장의 포지션을 넣으면 몇 번째 인덱스인지 반환
//        Timber.tag("kang").d("vm-getSelectedItemIndex")
//        val iterator = selectedItemData.value!!.iterator()
//        var count = 1
//        while (iterator.hasNext()) {
//            count++
//            if (position == iterator.next()) {
//                Timber.tag("kang").e("vm-${selectedItemData.value}, count - $count")
//                removeItem(item)
//                return count
//            }
//        }
//        Timber.tag("kang")
//            .e("vm-${selectedItemData.value}, size - ${selectedItemData.value!!.size}")
//        addItem(item)
//        return selectedItemData.value!!.size
//    }

    private fun initShelfData() {
        _pickShelfData.value = listOf(
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
