package com.sopt.peekabookaos.presentation.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.RecommendEntity
import com.sopt.peekabookaos.domain.usecase.GetRecommendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val getRecommendUseCase: GetRecommendUseCase
) : ViewModel() {
    private val _recommendedBook = MutableLiveData<List<RecommendEntity>>()
    val recommendedBook: LiveData<List<RecommendEntity>> = _recommendedBook

    private val _recommendingBook = MutableLiveData<List<RecommendEntity>>()
    val recommendingBook: LiveData<List<RecommendEntity>> = _recommendingBook

    fun getRecommend() {
        viewModelScope.launch {
            getRecommendUseCase()
                .onSuccess { response ->
                    _recommendingBook.value = response.recommendingBook
                    _recommendedBook.value = response.recommendedBook
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}
