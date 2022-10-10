package com.zjf.clear.ui.viewmodel

import android.app.Activity
import android.app.Application
import com.zjf.clear.ui.state.SimpleAdUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * create by colin
 * 2022/8/14
 */
open class SimpleCompleteViewModel(app: Application) : SimpleAdViewModel(app) {

    private val _uiState = MutableStateFlow(SimpleAdUiState(false))
    val uiState = _uiState.asStateFlow()


    fun loadWaitAd(activity: Activity, adUnit: String) {
        val ad = createInsertAd(activity, adUnit) {
            onComplete()
        }
        launchNetRequest {
            val success = loadAdWithTimeout(ad)
            if (success != null && ad.isReady) {
                ad.showAd()
            } else {
                onComplete()
            }
        }
    }

    private fun onComplete() {
        _uiState.update {
            it.copy(isComplete = true)
        }
    }
}