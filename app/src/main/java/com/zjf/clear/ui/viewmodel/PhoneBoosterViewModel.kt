package com.zjf.clear.ui.viewmodel

import android.app.Application
import com.zjf.clear.ui.state.PhoneBoosterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * create by colin
 * 2022/8/14
 */
class PhoneBoosterViewModel(app: Application) : SimpleAdViewModel(app) {

    private val _uiState = MutableStateFlow(PhoneBoosterUiState(0f))
    val uiState = _uiState.asStateFlow()


}