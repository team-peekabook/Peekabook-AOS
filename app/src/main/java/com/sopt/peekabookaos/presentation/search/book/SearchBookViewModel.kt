package com.sopt.peekabookaos.presentation.search.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.SelfIntro
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchBookViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Book>())
    val uiState = _uiState.asStateFlow()

    private val _friendInfo = MutableLiveData<SelfIntro>()
    val friendInfo: LiveData<SelfIntro> = _friendInfo

    private val _isServerStatus = MutableSharedFlow<Boolean>()
    val isServerStatus = _isServerStatus.asSharedFlow()

    /* 서버 통신 시 제거 예정 */
    private val serverStatus = true

    val bookTitle = MutableStateFlow("")

    fun searchBtnClickListener() {
        /* 서버 통신 시 구현 예정*/
        viewModelScope.launch {
            if (serverStatus) {
                _isServerStatus.emit(true)
                _uiState.value = dummy
            } else {
                _isServerStatus.emit(false)
            }
        }
    }

    fun initFriendInfo(friend: SelfIntro) {
        _friendInfo.value = friend
    }

    companion object {
        private val dummy = listOf(
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회"
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 가을",
                author = "김신회"
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회"
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 가을",
                author = "김신회"
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회"
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회"
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 가을",
                author = "김신회"
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 여름",
                author = "김신회"
            ),
            Book(
                bookImage = "http://image.yes24.com/goods/90365124/XL",
                bookTitle = "아무튼, 가을",
                author = "김신회"
            )
        )
    }
}
