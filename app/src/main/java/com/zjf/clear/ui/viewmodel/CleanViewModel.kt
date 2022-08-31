package com.zjf.clear.ui.viewmodel

import android.app.Application
import com.zjf.clear.data.repository.GlobalRepository
import com.zjf.clear.ui.state.CleanUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

/**
 * create by colin
 * 2022/8/14
 */
class CleanViewModel(app: Application) : SimpleAdViewModel(app) {

    private val _uiState = MutableStateFlow(CleanUiState(0f, "MB"))
    val uiState = _uiState.asStateFlow()


    init {

        val until = if (GlobalRepository.isRecentInClean()) {
            Pair(200, "MB")
        } else {
            Pair(10, "GB")
        }
        val floatRandomNum =
            Random(System.currentTimeMillis()).nextInt(until.first) + Random(System.currentTimeMillis()).nextFloat()
        _uiState.update {
            it.copy(size = floatRandomNum, unit = until.second)
        }

        launchNetRequest {
            delay(6000)
            _uiState.update {
                it.copy(isComplete = true)
            }
        }
    }

}