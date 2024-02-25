package com.sopt.peekabookaos.presentation.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.Recommend
import com.sopt.peekabookaos.domain.usecase.GetRecommendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val getRecommendUseCase: GetRecommendUseCase
) : ViewModel() {
    private val _recommendedBook = MutableLiveData<List<Recommend>>()
    val recommendedBook: LiveData<List<Recommend>> = _recommendedBook

    private val _recommendingBook = MutableLiveData<List<Recommend>>()
    val recommendingBook: LiveData<List<Recommend>> = _recommendingBook

    private val _isEditMode = MutableLiveData(false)
    val isEditMode: LiveData<Boolean> = _isEditMode

    private val _recommendId = MutableLiveData(-1)
    val recommendId: LiveData<Int> = _recommendId

    fun toggleEditMode() {
        _isEditMode.value = _isEditMode.value?.not()
        val updatedRecommendedBooks =
            _recommendedBook.value?.map { it.copy(isEditMode = _isEditMode.value!!) }
        _recommendedBook.value = updatedRecommendedBooks!!

        val updatedRecommendingBooks =
            _recommendingBook.value?.map { it.copy(isEditMode = _isEditMode.value!!) }
        _recommendingBook.value = updatedRecommendingBooks!!
    }

    fun deleteRecommend() {

    }

    fun setRecommendId(recommendId: Int) {
        _recommendId.value = recommendId
    }

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
