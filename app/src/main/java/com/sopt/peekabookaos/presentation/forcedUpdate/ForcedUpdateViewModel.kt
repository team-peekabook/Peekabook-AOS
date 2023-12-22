package com.sopt.peekabookaos.presentation.forcedUpdate

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.entity.UpdateInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForcedUpdateViewModel : ViewModel() {
    private val _updateInformation = MutableStateFlow(UpdateInformation())
    val updateInformation = _updateInformation.asStateFlow()

    fun setUpdateInformation(updateInformation: UpdateInformation) =
        _updateInformation.update { updateInformation }
}
