package com.sopt.peekabookaos.presentation.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.Recommend
import com.sopt.peekabookaos.domain.usecase.DeleteRecommendUseCase
import com.sopt.peekabookaos.domain.usecase.GetRecommendUseCase
import com.sopt.peekabookaos.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val getRecommendUseCase: GetRecommendUseCase,
    private val deleteRecommendUseCase: DeleteRecommendUseCase
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _recommendedBook = MutableLiveData<List<Recommend>>()
    val recommendedBook: LiveData<List<Recommend>> = _recommendedBook

    private val _recommendingBook = MutableLiveData<List<Recommend>>()
    val recommendingBook: LiveData<List<Recommend>> = _recommendingBook

//    private val _isEditMode = MutableLiveData(false)
//    val isEditMode: LiveData<Boolean> = _isEditMode

    private val _recommendId = MutableLiveData(-1)
    val recommendId: LiveData<Int> = _recommendId

    private val _isEditMode = MediatorLiveData<Boolean>().apply {
        value = false

        addSource(_recommendedBook) { value = checkEditMode(it, _recommendingBook.value) }
        addSource(_recommendingBook) { value = checkEditMode(_recommendedBook.value, it) }
    }
    val isEditMode: LiveData<Boolean> = _isEditMode

    private fun checkEditMode(
        recommendedBooks: List<Recommend>?,
        recommendingBooks: List<Recommend>?
    ): Boolean {
        val recommendedBooksEmpty = recommendedBooks.isNullOrEmpty()
        val recommendingBooksEmpty = recommendingBooks.isNullOrEmpty()

        return if (recommendedBooksEmpty && recommendingBooksEmpty) false else _isEditMode.value
            ?: false
    }

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
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.IDLE)
            deleteRecommendUseCase(requireNotNull(_recommendId.value))
                .onSuccess {
                    _uiEvent.emit(UiEvent.SUCCESS)
                }.onFailure { throwable ->
                    _uiEvent.emit(UiEvent.ERROR)
                    Timber.e("$throwable")
                }
        }
    }

    fun setRecommendId(recommendId: Int) {
        _recommendId.value = recommendId
    }

    fun getRecommend() {
        viewModelScope.launch {
            getRecommendUseCase()
                .onSuccess { response ->
                    _recommendingBook.value =
                        response.recommendingBook.map { it.copy(isEditMode = _isEditMode.value!!) }
                    _recommendedBook.value =
                        response.recommendedBook.map { it.copy(isEditMode = _isEditMode.value!!) }
                    checkEditMode(_recommendingBook.value, _recommendedBook.value)
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}
