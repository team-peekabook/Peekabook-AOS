package com.sopt.peekabookaos.presentation.search.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.SelfIntro
import com.sopt.peekabookaos.data.repository.NaverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val naverRepository: NaverRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Book>())
    val uiState = _uiState.asStateFlow()

    private val _friendInfo = MutableLiveData<SelfIntro>()
    val friendInfo: LiveData<SelfIntro> = _friendInfo

    private val _isCreateView = MutableLiveData<Boolean>()
    val isCreateView: LiveData<Boolean> = _isCreateView

    private val _isSearch = MutableSharedFlow<Boolean>()
    val isSearch = _isSearch.asSharedFlow()

    val bookTitle = MutableStateFlow("")

    fun searchBtnClickListener() {
        viewModelScope.launch {
            naverRepository.getBookToTitle(bookTitle.value)
                .onSuccess { response ->
                    _uiState.value = response
                    if (response.isNullOrEmpty()) {
                        _isSearch.emit(false)
                    } else {
                        _isSearch.emit(true)
                    }
                }.onFailure {
                    _isSearch.emit(false)
                }
        }
    }

    fun initFriendInfo(friend: SelfIntro) {
        _friendInfo.value = friend
    }

    fun initIsCreateView(create: Boolean) {
        _isCreateView.value = create
    }
}
