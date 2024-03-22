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

    private val _recommendId = MutableLiveData(-1)
    val recommendId: LiveData<Int> = _recommendId

    private val _isEditMode = MediatorLiveData<Boolean>().apply {
        value = false

        addSource(_recommendedBook) { recommendedBooks ->
            value = checkEditMode(recommendedBooks, _recommendingBook.value ?: emptyList())
        }
        addSource(_recommendingBook) { recommendingBooks ->
            value = checkEditMode(_recommendedBook.value ?: emptyList(), recommendingBooks)
        }
    }
    val isEditMode: LiveData<Boolean> = _isEditMode

    private fun checkEditMode(
        recommendedBooks: List<Recommend>,
        recommendingBooks: List<Recommend>
    ): Boolean {
        val recommendedBooksEmpty = recommendedBooks.isEmpty()
        val recommendingBooksEmpty = recommendingBooks.isEmpty()

        return if (recommendedBooksEmpty && recommendingBooksEmpty) false else _isEditMode.value
            ?: false
    }

    fun toggleEditMode() {
        _isEditMode.value = _isEditMode.value?.not() ?: false
        val updatedRecommendedBooks =
            _recommendedBook.value?.map { it.copy(isEditMode = _isEditMode.value ?: false) }
        _recommendedBook.value = updatedRecommendedBooks ?: emptyList()

        val updatedRecommendingBooks =
            _recommendingBook.value?.map { it.copy(isEditMode = _isEditMode.value ?: false) }
        _recommendingBook.value = updatedRecommendingBooks ?: emptyList()
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
                    _isEditMode.value = false
                    _recommendingBook.value = response.recommendingBook
                    _recommendedBook.value = response.recommendedBook
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}
