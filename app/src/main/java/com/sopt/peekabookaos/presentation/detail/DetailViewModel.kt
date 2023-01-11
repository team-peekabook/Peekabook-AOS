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
    private val _detailData = MutableLiveData<List<Detail>>()
    val detailData: LiveData<List<Detail>> = _detailData

    private val _isMyDetailView = MutableLiveData<Boolean>()
    val isMyDetailView: LiveData<Boolean> = _isMyDetailView

    private val bookId = MutableLiveData<Int>()

    init {
        getDetail()
    }

    fun initIsMyDetailView(detail: Boolean) {
        _isMyDetailView.value = detail
    }

    fun initBookId(id: Int) {
        bookId.value = id
    }

    private fun getDetail() {
        viewModelScope.launch {
            detailRepository.getDetail()
                .onSuccess { response ->
                    _detailData.value = response.detailBook
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}
