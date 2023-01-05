package com.sopt.peekabookaos.presentation.pickModify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Shelf

class PickModifyViewModel:ViewModel() {
    private val _pickShelfData: MutableLiveData<List<Shelf>> = MutableLiveData()
    val pickShelfData: LiveData<List<Shelf>> = _pickShelfData

    init {
        initShelfData()
    }

    private fun initShelfData() {
        _pickShelfData.value = listOf(
            Shelf("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf("https://image.yes24.com/goods/74261416/XL", false),
            Shelf("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf("https://image.yes24.com/goods/74261416/XL", false),
            Shelf("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf("https://image.yes24.com/goods/74261416/XL", false),
            Shelf("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf("https://image.yes24.com/goods/74261416/XL", false)
        )
    }
}