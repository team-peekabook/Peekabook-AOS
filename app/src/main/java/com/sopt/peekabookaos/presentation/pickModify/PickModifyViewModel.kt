package com.sopt.peekabookaos.presentation.pickModify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.peekabookaos.domain.entity.Picks
import com.sopt.peekabookaos.domain.usecase.GetPickUseCase
import com.sopt.peekabookaos.domain.usecase.PatchPickUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PickModifyViewModel @Inject constructor(
    private val patchPickUseCase: PatchPickUseCase,
    private val getPickUseCase: GetPickUseCase
) : ViewModel() {
    private val _pickModifyData: MutableLiveData<List<Picks>> = MutableLiveData()
    val pickModifyData: LiveData<List<Picks>> = _pickModifyData

    private val _selectedItemList: MutableLiveData<LinkedHashSet<Int>> = MutableLiveData(
        linkedSetOf()
    )

    private val _selectState: MutableLiveData<Boolean> = MutableLiveData()
    val selectState: LiveData<Boolean> = _selectState

    private val _overListState: MutableLiveData<Boolean> = MutableLiveData()
    val overListState: LiveData<Boolean> = _overListState

    var preListState = _overListState.value

    private val _isGetPickServerStatus = MutableLiveData<Boolean>()
    val isGetPickServerStatus: LiveData<Boolean> = _isGetPickServerStatus

    private val _isPatchPickServerStatus = MutableLiveData<Boolean>()
    val isPatchPickServerStatus: LiveData<Boolean> = _isPatchPickServerStatus

    var selectItemIdList = arrayOfNulls<Int>(3)

    init {
        getPick()
    }

    fun updateSelectedItemState(item: Picks) {
        preListState = _selectedItemList.value?.size!! >= 3
        if (item.pickIndex == 0 && _selectedItemList.value?.size!! < 3) {
            item.pickIndex = _selectedItemList.value?.size!! + 1
            _selectedItemList.value?.add(item.id)
            _selectState.value = true
        } else {
            _selectedItemList.value!!.remove(item.id)
            item.pickIndex = 0
            _selectState.value = false
            updateSelectedItemIndex()
        }
        _overListState.value = (_selectedItemList.value?.size!! >= 3 && preListState == true)
    }

    private fun initSelectedItemList(data: List<Picks>) {
        for (item in data) {
            if (item.pickIndex != 0) {
                _selectedItemList.value?.add(item.id)
            }
        }
    }

    private fun updateSelectedItemIndex() {
        for (item in _pickModifyData.value!!) {
            if (_selectedItemList.value?.contains(item.id) == true) {
                item.pickIndex = getSelectedItemIndex(item)
            }
        }
    }

    fun changeHashToPickRequest() {
        val iterator = _selectedItemList.value!!.iterator()
        var count = 0
        while (iterator.hasNext()) {
            selectItemIdList[count] = iterator.next()
            count++
        }
    }

    private fun getSelectedItemIndex(item: Picks): Int {
        val iterator = _selectedItemList.value!!.iterator()
        var count = 0
        while (iterator.hasNext()) {
            count++
            if (item.id == iterator.next()) {
                return count
            }
        }
        return count
    }

    private fun getPick() {
        viewModelScope.launch {
            getPickUseCase()
                .onSuccess { response ->
                    _pickModifyData.value = response
                    initSelectedItemList(response)
                    _isGetPickServerStatus.value = true
                }.onFailure { throwable ->
                    _isGetPickServerStatus.value = false
                    Timber.e("$throwable")
                }
        }
    }

    fun patchPick() {
        viewModelScope.launch {
            patchPickUseCase(
                selectItemIdList[0] ?: 0,
                selectItemIdList[1] ?: 0,
                selectItemIdList[2] ?: 0
            ).onSuccess { success ->
                _isPatchPickServerStatus.value = success
            }.onFailure(Timber::e)
        }
    }
}
