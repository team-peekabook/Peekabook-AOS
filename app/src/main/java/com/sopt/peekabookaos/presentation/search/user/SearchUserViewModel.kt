package com.sopt.peekabookaos.presentation.search.user

import com.sopt.peekabookaos.data.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
class SearchUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUserUiState())
    val uiState = _uiState.asStateFlow()

}
