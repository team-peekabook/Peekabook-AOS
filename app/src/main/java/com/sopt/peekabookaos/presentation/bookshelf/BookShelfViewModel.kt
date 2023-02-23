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

    private val _lastSelectedItem: MutableLiveData<Int> = MutableLiveData()
    var lastSelectedItem: LiveData<Int> = _lastSelectedItem

    private val _bookTotalNum: MutableLiveData<Int> = MutableLiveData(0)
    var bookTotalNum: LiveData<Int> = _bookTotalNum

    private val _isMyServerStatus = MutableLiveData<Boolean>()
    val isMyServerStatus: LiveData<Boolean> = _isMyServerStatus

    private val _isFriendServerStatus = MutableLiveData<Boolean>()
    val isFriendServerStatus: LiveData<Boolean> = _isFriendServerStatus

    init {
        getMyShelfData()
    }

    fun updateShelfState(state: Boolean) {
        _friendShelf.value = state
    }

    fun updateUserId(item: FriendList) {
        _userId.value = item.id
    }

    fun updateLastSelectedItem(position: Int) {
        _lastSelectedItem.value = position
    }

    fun getMyShelfData() {
        viewModelScope.launch {
            shelfRepository.getMyShelf()
                .onSuccess { response ->
                    _pickData.value = response.picks
                    _bookTotalNum.value = response.bookTotalNum
                    _shelfData.value = response.books
                    _friendUserData.value = response.friendList
                    _userData.value = response.myIntro
                    _isMyServerStatus.value = true
                    _isFriendServerStatus.value = false
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                    _isMyServerStatus.value = false
                    _isFriendServerStatus.value = false
                }
        }
    }

    fun getFriendShelfData() {
        viewModelScope.launch {
            shelfRepository.getFriendShelf(userId.value!!)
                .onSuccess { response ->
                    _friendUserData.value = response.friendList
                    _friendData.value = response.friendIntro
                    _pickData.value = response.picks
                    _bookTotalNum.value = response.bookTotalNum
                    _shelfData.value = response.books
                    _isFriendServerStatus.value = true
                    _isMyServerStatus.value = false
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                    _isFriendServerStatus.value = false
                    _isMyServerStatus.value = false
                }
        }
    }
}
