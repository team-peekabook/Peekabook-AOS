package com.sopt.peekabookaos.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Detail
import com.sopt.peekabookaos.data.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _detailData = MutableLiveData<Detail>()
    val detailData: LiveData<Detail> = _detailData

    private val _isMyDetailView = MutableLiveData<Boolean>()
    val isMyDetailView: LiveData<Boolean> = _isMyDetailView

    val bookId = MutableLiveData<Int>()

    fun initIsMyDetailView(detail: Boolean) {
        _isMyDetailView.value = detail
    }

    fun initBookId(id: Int) {
        bookId.value = id
    }

    fun getDetail(bookId: Int) {
        viewModelScope.launch {
            detailRepository.getDetail(bookId)
                .onSuccess { response ->
                    _detailData.value = response
                    Timber.e("$response")
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    fun delete() {
        /** 서버통신 함수 여기에 만들면 되고 함수명은 수정하세요 ~ */
    }
}
