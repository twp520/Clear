package com.zjf.clear.ui.viewmodel

import android.app.Application
import com.zjf.clear.ui.state.SimpleAdUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * create by colin
 * 2022/8/14
 */
class SimpleCompleteViewModel(app: Application) : SimpleAdViewModel(app) {

    private val _uiState = MutableStateFlow(SimpleAdUiState(false))
    val uiState = _uiState.asStateFlow()


    init {
        launchNetRequest {
            delay(4500)
            _uiState.update {
                it.copy(isComplete = true)
            }
        }
    }
}