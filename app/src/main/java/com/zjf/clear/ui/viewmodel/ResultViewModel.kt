package com.zjf.clear.ui.viewmodel

import android.app.Application
import com.zjf.clear.data.ResultRepository
import com.zjf.clear.ui.state.ResultUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * create by colin
 * 2022/8/15
 */
class ResultViewModel(app: Application) : SimpleAdViewModel(app) {

    private val _uiState = MutableStateFlow(ResultUiState("", mutableListOf()))
    val uiState = _uiState.asStateFlow()


    private val repository: ResultRepository = ResultRepository()

    fun fetchData(funcId: Int) {

        val allData = repository.getAllFuncList(getApplication())
        val listData = allData.filter { it.id != funcId }
        val title = allData.find { it.id == funcId }?.displayName ?: ""

        _uiState.update {
            it.copy(title = title, listData = listData)
        }
    }
}