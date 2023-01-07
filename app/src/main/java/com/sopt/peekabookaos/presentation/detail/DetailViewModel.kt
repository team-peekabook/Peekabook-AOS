package com.sopt.peekabookaos.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Book

class DetailViewModel : ViewModel() {
    private val _bookData = MutableLiveData<Book>()
    val bookData: LiveData<Book> = _bookData

    init {
        initDetailData()
    }

    private fun initDetailData() {
        _bookData.value = Book(
            bookImage = "http://image.yes24.com/goods/90365124/XL",
            bookTitle = "아무튼, 여름",
            author = "김신회",
            description = "안드로이드 스튜디오에서 구현되는 다이얼로그에 대해 알아봅시다. 구글에서 완전히 열심히 찾아보면 갤럭시나 아이폰을 사용하면 다이얼로그를 잘 알것이다. 어떤 버튼을 클릭했을 때나 이벤트가 실행되거나 완료되면 어떤 확인문구와 함께 작게 뜨는 창을 본적이 있을 것이다. 그것이 다이얼로그다. 다이얼로그에는 종류가 정말 많은데 코드가 어떻게 구현되는지 알아보도록 하자",
            memo = ""
        )
    }
}
