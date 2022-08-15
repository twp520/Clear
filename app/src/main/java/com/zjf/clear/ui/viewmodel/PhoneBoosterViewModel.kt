package com.zjf.clear.ui.viewmodel

import android.app.Application
import com.zjf.clear.ui.state.PhoneBoosterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

/**
 * create by colin
 * 2022/8/14
 */
class PhoneBoosterViewModel(app: Application) : SimpleAdViewModel(app) {

    private val _uiState = MutableStateFlow(PhoneBoosterUiState(0f))
    val uiState = _uiState.asStateFlow()


    init {
        val floatRandomNum =
            Random(System.currentTimeMillis()).nextInt(10) + Random(System.currentTimeMillis()).nextFloat()
        _uiState.update {
            it.copy(size = floatRandomNum)
        }
    }

}