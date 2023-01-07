package com.sopt.peekabookaos.presentation.pickModify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Shelf

class PickModifyViewModel : ViewModel() {
    private val _pickShelfData: MutableLiveData<List<Shelf>> = MutableLiveData()
    val pickShelfData: LiveData<List<Shelf>> = _pickShelfData

    private val _selectedItemIdData: MutableLiveData<LinkedHashSet<Int>> = MutableLiveData(
        linkedSetOf()
    )
    val selectedItemData: LiveData<LinkedHashSet<Int>> = _selectedItemIdData

    private val _itemSelectState: MutableLiveData<Boolean> = MutableLiveData()
    val itemSelectState: LiveData<Boolean> = _itemSelectState

    var position: Int = -1

    init {
        initShelfData()
    }

    fun updateSelectedItem(pos: Int, item: Shelf) {
        position = pos
        if (_selectedItemIdData.value?.contains(item.bookId) == true) {
            _itemSelectState.value = !(_selectedItemIdData.value!!.remove(item.bookId))
        } else {
            if (_selectedItemIdData.value!!.size <= 2) {
                _itemSelectState.value =
                    (_selectedItemIdData.value?.add(item.bookId) == true)
            } else {
                _itemSelectState.value = false
            }
        }
    }
    fun getSelectedItemCount(): Int {
        return selectedItemData.value!!.size
    }
    private fun initShelfData() {
        _pickShelfData.value = listOf(
            Shelf(1, "http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf(2, "https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                3,
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf(4, "https://image.yes24.com/goods/74261416/XL", false),
            Shelf(5, "http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf(6, "https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                7,
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf(8, "https://image.yes24.com/goods/74261416/XL", false),
            Shelf(9, "http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf(10, "https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                11,
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf(12, "https://image.yes24.com/goods/74261416/XL", false),
            Shelf(13, "http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf(14, "https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                15,
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf(16, "https://image.yes24.com/goods/74261416/XL", false)
        )
    }
}
