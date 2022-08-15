package com.zjf.clear.ui.viewmodel

import android.app.Application
import com.beeline.common.BaseViewModel
import com.zjf.clear.ui.state.WelcomeUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * create by colin
 * 2022/8/11
 */
class WelcomeViewModel(app: Application) : BaseViewModel(app) {

    private val _uiState = MutableStateFlow(WelcomeUiState(false))
    val uiState = _uiState.asStateFlow()

    init {
        requestInit()
    }

    private fun requestInit() {
        launchNetRequest {
            delay(1000)
            _uiState.update {
                it.copy(isInitComplete = true)
            }
        }

    }
}