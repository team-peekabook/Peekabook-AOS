package com.sopt.peekabookaos.presentation.notificationBookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.Books
import com.sopt.peekabookaos.domain.entity.Notification
import com.sopt.peekabookaos.domain.entity.Picks
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.usecase.DeleteFollowUseCase
import com.sopt.peekabookaos.domain.usecase.GetFriendShelfUseCase
import com.sopt.peekabookaos.domain.usecase.PostBlockUseCase
import com.sopt.peekabookaos.domain.usecase.PostFollowUseCase
import com.sopt.peekabookaos.presentation.notification.NotificationFragment.Companion.FOLLOWER_ONLY
import com.sopt.peekabookaos.presentation.notification.NotificationFragment.Companion.FOLLOW_EACH_OTHER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationBookShelfViewModel @Inject constructor(
    private val getFriendShelfUseCase: GetFriendShelfUseCase,
    private val postBlockUseCase: PostBlockUseCase,
    private val deleteFollowUseCase: DeleteFollowUseCase,
    private val postFollowUseCase: PostFollowUseCase
) : ViewModel() {
    private val _pickData: MutableLiveData<List<Picks>> = MutableLiveData()
    val pickData: LiveData<List<Picks>> = _pickData

    private val _shelfData: MutableLiveData<List<Books>> = MutableLiveData()
    val shelfData: LiveData<List<Books>> = _shelfData

    private val _friendData: MutableLiveData<User> = MutableLiveData()
    val friendData: LiveData<User> = _friendData

    private val _userId: MutableLiveData<Int> = MutableLiveData(0)
    private var userId: LiveData<Int> = _userId

    private val _bookTotalNum: MutableLiveData<Int> = MutableLiveData(0)
    var bookTotalNum: LiveData<Int> = _bookTotalNum

    private val _isFollow = MutableLiveData<Boolean>()
    val isFollow: LiveData<Boolean> = _isFollow

    private val _isUnfollowStatus = MutableLiveData(false)
    val isUnfollowStatus: LiveData<Boolean> = _isUnfollowStatus

    private val _isFollowSuccess = MutableLiveData(false)
    val isFollowSuccess: LiveData<Boolean> = _isFollowSuccess

    private val _isBlockStatus = MutableLiveData(false)
    val isBlockStatus: LiveData<Boolean> = _isBlockStatus

    init {
        getFriendShelfData()
    }

    fun initUserInfo(notification: Notification) {
        _userId.value = notification.senderId
        when (notification.typeId) {
            FOLLOW_EACH_OTHER -> {
                _isFollow.value = true
            }

            FOLLOWER_ONLY -> {
                _isFollow.value = false
            }
        }
        getFriendShelfData()
    }

    private fun getFriendShelfData() {
        viewModelScope.launch {
            getFriendShelfUseCase(requireNotNull(userId.value))
                .onSuccess { response ->
                    _friendData.value = response.friendIntro
                    _pickData.value = response.picks
                    _bookTotalNum.value = response.bookTotalNum
                    _shelfData.value = response.books
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    fun postUnfollow() {
        viewModelScope.launch {
            deleteFollowUseCase(requireNotNull(userId.value))
                .onSuccess { response ->
                    _isUnfollowStatus.value = response
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    fun postFollow() {
        viewModelScope.launch {
            postFollowUseCase(requireNotNull(userId.value))
                .onSuccess { response ->
                    _isFollowSuccess.value = response
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    fun postBlock() {
        viewModelScope.launch {
            postBlockUseCase(requireNotNull(userId.value))
                .onSuccess { response ->
                    _isBlockStatus.value = response
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}
