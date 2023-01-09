package com.sopt.peekabookaos.presentation.pickModify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Shelf
import timber.log.Timber

class PickModifyViewModel : ViewModel() {
    private val _pickShelfData: MutableLiveData<List<Shelf>> = MutableLiveData()
    val pickShelfData: LiveData<List<Shelf>> = _pickShelfData

    private val _selectedItemIdData: MutableLiveData<LinkedHashSet<Int>> = MutableLiveData(
        linkedSetOf()
    )
    private val selectedItemData: LiveData<LinkedHashSet<Int>> = _selectedItemIdData

    private val _itemSelectState: MutableLiveData<Boolean> = MutableLiveData()
    val itemSelectState: LiveData<Boolean> = _itemSelectState

    var position: Int = -1

    init {
        initShelfData()
    }

    fun updateSelectedItem(pos: Int, item: Shelf) {
        position = pos
        if (_selectedItemIdData.value?.contains(item.bookId) == true) { // bookId에 이미 존재하면 제거
            _itemSelectState.value = false
        } else { // 존재하지 않고
            _itemSelectState.value = _selectedItemIdData.value!!.size <= 2
        }
    }

    fun removeItem(item: Shelf) {
        _selectedItemIdData.value!!.remove(item.bookId)
    }

    fun addItem(item: Shelf) {
        _selectedItemIdData.value?.add(item.bookId)
    }

    fun getSelectedItemIndex(position: Int, item: Shelf): Int { // 책장의 포지션을 넣으면 몇 번째 인덱스인지 반환
        Timber.tag("kang").d("vm-getSelectedItemIndex")
        val iterator = selectedItemData.value!!.iterator()
        var count = 1
        while (iterator.hasNext()) {
            count++
            if (position == iterator.next()) {
                Timber.tag("kang").e("vm-${selectedItemData.value}, count - $count")
                removeItem(item)
                return count
            }
        }
        Timber.tag("kang")
            .e("vm-${selectedItemData.value}, size - ${selectedItemData.value!!.size}")
        addItem(item)
        return selectedItemData.value!!.size
    }

    private fun initShelfData() {
        _pickShelfData.value = listOf(
            Shelf(1, "https://image.yes24.com/goods/76106687/XL", false),
            Shelf(2, "https://image.yes24.com/goods/114671132/XL", true),
            Shelf(
                3,
                "https://image.yes24.com/goods/97255028/XL",
                false
            ),
            Shelf(4, "https://image.yes24.com/momo/TopCate215/MidCate002/21414510.jpg", true),
            Shelf(5, "https://image.yes24.com/goods/72127217/XL", false),
            Shelf(
                6,
                "https://image.yes24.com/goods/91159773/XL",
                false
            ),
            Shelf(
                7,
                "https://blog.kakaocdn.net/dn/66RoG/btqSGiU51MK/4NZO9mMJnAoFHmjXtSGiuK/img.png",
                false
            ),
            Shelf(8, "https://image.yes24.com/goods/72310907/XL", true),
            Shelf(9, "https://image.yes24.com/goods/76106687/XL", false),
            Shelf(10, "https://image.yes24.com/goods/114671132/XL", true),
            Shelf(
                11,
                "https://image.yes24.com/goods/97255028/XL",
                false
            ),
            Shelf(12, "https://image.yes24.com/momo/TopCate215/MidCate002/21414510.jpg", true),
            Shelf(13, "https://image.yes24.com/goods/72127217/XL", false),
            Shelf(
                14,
                "https://image.yes24.com/goods/91159773/XL",
                false
            ),
            Shelf(
                15,
                "https://blog.kakaocdn.net/dn/66RoG/btqSGiU51MK/4NZO9mMJnAoFHmjXtSGiuK/img.png",
                false
            ),
            Shelf(16, "https://image.yes24.com/goods/72310907/XL", true)
        )
    }
}
