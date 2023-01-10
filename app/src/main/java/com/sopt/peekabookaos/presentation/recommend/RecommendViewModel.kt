package com.sopt.peekabookaos.presentation.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Recommend
import com.sopt.peekabookaos.data.repository.RecommendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val recommendRepository: RecommendRepository
) : ViewModel() {
    private val _recommendedBook = MutableLiveData<List<Recommend>>()
    val recommendedBook: LiveData<List<Recommend>> = _recommendedBook

    private val _recommendingBook = MutableLiveData<List<Recommend>>()
    val recommendingBook: LiveData<List<Recommend>> = _recommendingBook
