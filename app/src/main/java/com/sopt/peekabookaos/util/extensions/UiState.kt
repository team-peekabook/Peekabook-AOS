package com.sopt.peekabookaos.util.extensions

sealed class UiState<out T> {
    object IDLE : UiState<Nothing>()

    data class Success<T>(
        val data: T
    ) : UiState<T>()

    data class Error(
        val error: Throwable
    ) : UiState<Nothing>()
}

inline fun <reified T : Any> UiState<T>.onSuccess(action: (data: T) -> Unit): UiState<T> {
    if (this is UiState.Success<T>) {
        action(data)
    }
    return this
}

inline fun <reified T : Any> UiState<T>.onFailed(action: (exception: Throwable) -> Unit): UiState<T> {
    if (this is UiState.Error) {
        action(error)
    }
    return this
}

/*
// IDLE 상태 처리가 필요할 때 주석 제거하기!!
 inline fun <reified T : Any> UiState<T>.onIDLE(action: () -> Unit): UiState<T> {
    if (this is UiState.IDLE) {
        action()
    }
    return this
 }
*/
