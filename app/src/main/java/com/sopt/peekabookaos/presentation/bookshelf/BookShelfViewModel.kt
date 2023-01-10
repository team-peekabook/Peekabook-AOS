package com.sopt.peekabookaos.presentation.bookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Books
import com.sopt.peekabookaos.data.entity.FriendList
import com.sopt.peekabookaos.data.entity.Picks
import com.sopt.peekabookaos.data.entity.SelfIntro
import com.sopt.peekabookaos.data.repository.ShelfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookShelfViewModel @Inject constructor(
    private val shelfRepository: ShelfRepository
) : ViewModel() {
    private val _pickData: MutableLiveData<List<Picks>> = MutableLiveData()
    val pickData: LiveData<List<Picks>> = _pickData

    private val _shelfData: MutableLiveData<List<Books>> = MutableLiveData()
    val shelfData: LiveData<List<Books>> = _shelfData

    private val _friendUserData: MutableLiveData<List<FriendList>> = MutableLiveData()
    val friendUserData: LiveData<List<FriendList>> = _friendUserData

    private val _userData: MutableLiveData<SelfIntro> = MutableLiveData()
    val userData: LiveData<SelfIntro> = _userData

    private val _friendData: MutableLiveData<SelfIntro> = MutableLiveData()
    val friendData: LiveData<SelfIntro> = _friendData

    private val _friendShelf: MutableLiveData<Boolean> = MutableLiveData(false)
    var friendShelf: LiveData<Boolean> = _friendShelf

    private val _userId: MutableLiveData<Int> = MutableLiveData(0)
    var userId: LiveData<Int> = _userId

    private val _bookTotalNum: MutableLiveData<Int> = MutableLiveData()
    var bookTotalNum: LiveData<Int> = _bookTotalNum

    private val _isServerStatus = MutableLiveData<Boolean>()
    val isServerStatus: LiveData<Boolean> = _isServerStatus

    init {
        getShelf()
    }

    fun updateShelfState(state: Boolean) {
        _friendShelf.value = state
    }

    fun updateUserId(position: Int) {
        _userId.value = position
    }

    private fun getShelf() {
        if (friendShelf.value == false) {
            viewModelScope.launch {
                shelfRepository.getMyShelf()
                    .onSuccess { response ->
                        _pickData.value = response.picks
                        _bookTotalNum.value = response.bookTotalNum
                        _shelfData.value = response.books
                        _friendUserData.value = response.friendList
                        _userData.value = response.myIntro
                        _isServerStatus.value = true
                    }.onFailure { throwable ->
                        Timber.e("$throwable")
                        _isServerStatus.value = false
                    }
            }
        }
    }
}
