package com.sopt.peekabookaos.presentation.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.entity.SelfIntro
import com.sopt.peekabookaos.domain.usecase.PostRecommendationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecommedationViewModel @Inject constructor(
    private val postRecommendationUseCase: PostRecommendationUseCase
) : ViewModel() {
    private val _bookData = MutableLiveData<Book>()
    val bookData: LiveData<Book> = _bookData

    private val _friendData = MutableLiveData<SelfIntro>()
    val friendData: LiveData<SelfIntro> = _friendData

    private val _isRecommendation = MutableLiveData<Boolean>()
    val isRecommendation: LiveData<Boolean> = _isRecommendation

    val comment = MutableLiveData("")

    fun initRecommendData(bookData: Book, friendData: SelfIntro) {
        _bookData.value = bookData
        _friendData.value = friendData
    }

    fun postRecommendation() {
        viewModelScope.launch {
            postRecommendationUseCase(
                recommendDesc = comment.value,
                bookTitle = _bookData.value!!.bookTitle,
                bookImage = _bookData.value!!.bookImage,
                author = _bookData.value!!.author,
                _friendData.value!!.id
            ).onSuccess {
                _isRecommendation.value = true
            }.onFailure { throwable ->
                _isRecommendation.value = false
                Timber.e("$throwable")
            }
        }
    }
}
