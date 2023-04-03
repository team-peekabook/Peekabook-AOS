package com.sopt.peekabookaos.presentation.withdraw

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.usecase.ClearLocalPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val clearLocalPrefUseCase: ClearLocalPrefUseCase
) : ViewModel() {
    /*
    TODO by 이빵주 : 서버통신 구현 시 MutableStateFlow 제거 후 MutableSharedFlow 사용 예정
    private val _isWithdraw = MutableSharedFlow<Boolean>()
    val isWithdraw = _isWithdraw.asSharedFlow()
    */

    private val _isWithdraw = MutableStateFlow(false)
    val isWithdraw = _isWithdraw.asStateFlow()

    fun deleteUser() {
        // TODO by 이빵주 : delete 서버통신
        _isWithdraw.value = true
        clearLocalPrefUseCase()
        // TODO by 이빵주 : 온보딩 상태 초기화
    }
}
