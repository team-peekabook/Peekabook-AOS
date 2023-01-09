package com.sopt.peekabookaos.presentation.pickModify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.PickModify
import timber.log.Timber

class PickModifyViewModel : ViewModel() {
    private val _pickModifyData: MutableLiveData<List<PickModify>> = MutableLiveData()
    val pickModifyData: LiveData<List<PickModify>> = _pickModifyData

    private val _selectedItemList: MutableLiveData<LinkedHashSet<Int>> = MutableLiveData(
        linkedSetOf()
    )
    val selectedItemList: LiveData<LinkedHashSet<Int>>? = _selectedItemList
    private val _selectState: MutableLiveData<Boolean> = MutableLiveData()
    val selectState: LiveData<Boolean> = _selectState
//    var position: Int = -1

    init {
        initPickModifyData()
        _pickModifyData.value?.let { initSelectedItemList(it) } // _selectedItemList에 선택되어 있는 item 입력하기
    }

    fun updateSelectedItemState(item: PickModify) { // 클릭했을 때, 아이템의 상태 업데이트를 위한 함수
        if (item.pickIndex == 0 && _selectedItemList.value?.size!! < 3) { // 고르지 않은 상태일 경우에
            item.pickIndex = _selectedItemList.value?.size!! + 1 ?: 1 // null이면 사이즈가 없으니까 인덱스는 1로
            _selectedItemList.value?.add(item.book.id) // hash에 추가
            _selectState.value = true
        } else { // 고른 경우
            _selectedItemList.value!!.remove(item.book.id) // hash에서 제거
            item.pickIndex = 0 // index 0으로 변경
            _selectState.value = false
            updateSelectedItem() // index 변경 고지
        }
        Timber.tag("kang").e("updateSelectedItemState: ${_selectedItemList.value}")
        Timber.tag("kang").d("updateSelectedItemState: ${_pickModifyData.value}")
    }

    private fun initSelectedItemList(data: List<PickModify>) { // _selectedItemList에 선택되어 있는 item 입력하기
        for (item in data) {
            if (item.pickIndex != 0) {
                _selectedItemList.value?.add(item.book.id)
            }
        }
    }

    private fun updateSelectedItem() { // 우선순위가 변동사항이 생기면 인덱스를 업데이트 하는 함수
        for (item in _pickModifyData.value!!) { // 데이터를 전부 넣어서
            if (_selectedItemList.value?.contains(item.book.id) == true) { // 선택hash에 있으면
                item.pickIndex = getSelectedItemIndex(item) // index 반환해서 index 새로고침
            }
        }
    }

    //
//    fun removeItem(item: Shelf) {
//        _selectedItemIdData.value!!.remove(item.bookId)
//    }
//
//    fun addItem(item: Shelf) {
//        _selectedItemIdData.value?.add(item.bookId)
//    }
//
    private fun getSelectedItemIndex(item: PickModify): Int { // 아이템을 넣으면 linkedHash에서 몇 번째 인덱스인지 반환
        val iterator = _selectedItemList.value!!.iterator()
        var count = 0
        while (iterator.hasNext()) {
            count++
            if (item.book.id == iterator.next()) {
                Timber.tag("kang").d("getSelectedItemIndex $count")
                return count
            }
        }
        Timber.tag("kang").d("getSelectedItemIndex $count")
        return count
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
