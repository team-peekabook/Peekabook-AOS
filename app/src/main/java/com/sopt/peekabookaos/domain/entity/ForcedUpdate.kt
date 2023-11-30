package com.sopt.peekabookaos.domain.entity

sealed class ForcedUpdate {
    data object None : ForcedUpdate()
    data class Need(val updateInformation: UpdateInformation) : ForcedUpdate()
}
