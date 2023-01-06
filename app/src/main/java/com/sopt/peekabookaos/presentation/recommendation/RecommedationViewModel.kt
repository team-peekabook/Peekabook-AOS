package com.sopt.peekabookaos.presentation.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Recommendation

class RecommedationViewModel : ViewModel() {
    private val _RecommendationData = MutableLiveData<Recommendation>()
    val RecommendationData: LiveData<Recommendation> = _RecommendationData

    val comment = MutableLiveData("")
}
