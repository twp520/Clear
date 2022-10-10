package com.zjf.clear.ui.viewmodel

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Application
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.lifecycle.viewModelScope
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.beeline.common.BaseViewModel
import com.blankj.utilcode.util.LogUtils
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.zjf.clear.R
import com.zjf.clear.ui.activity.WelcomeActivity
import com.zjf.clear.ui.listener.ClickMaxAdListenerImp
import com.zjf.clear.ui.state.WelcomeUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

/**
 * create by colin
 * 2022/8/11
 */
class WelcomeViewModel(app: Application) : BaseViewModel(app) {

    private val _uiState = MutableStateFlow(WelcomeUiState(false, 0))
    val uiState = _uiState.asStateFlow()


    fun initAd(activity: WelcomeActivity) {
        val first = ValueAnimator.ofInt(1, 60)
        first.interpolator = AccelerateDecelerateInterpolator()
        first.duration = 2500
        first.addUpdateListener {
            val value = it.animatedValue as Int
            _uiState.update { state ->
                state.copy(progress = value)
            }
        }
        first.start()

        launchNetRequest {
            LogUtils.d("开始初始化广告SDK")
            val success: Boolean? = withTimeoutOrNull(8000) {
                suspendCancellableCoroutine { continuation ->
                    // AudienceNetworkAds.initialize(getApplication())
                    // MobileAds.initialize(getApplication())
                    MobileAds.setRequestConfiguration(RequestConfiguration.Builder()
                        .setTestDeviceIds(mutableListOf("DF4B359FCE9BCAABE29B5E947CDD4D7E"))
                        .build())
                    AppLovinSdk.getInstance(getApplication()).mediationProvider =
                        AppLovinMediationProvider.MAX
                    AppLovinSdk.getInstance(activity).settings.testDeviceAdvertisingIds=
                        mutableListOf("71af2484-2811-42ba-8563-e489b631c868")
                    AppLovinSdk.getInstance(getApplication()).initializeSdk {
                        // AppLovin SDK is initialized, start loading ads
                        // AdSettings.setDataProcessingOptions(arrayOf<String>())
                        continuation.resume(true)
                    }
                }
            }
            LogUtils.d("广告SDK初始化结束：$success")
            startLoad(activity, first)
        }
    }

    private suspend fun startLoad(activity: WelcomeActivity, first: ValueAnimator) {

        val insAd = MaxInterstitialAd(activity.getString(R.string.ad_unit_splash), activity)
        insAd.setListener(ClickMaxAdListenerImp(insAd, viewModelScope) {
            _uiState.update {
                it.copy(isInitComplete = true)
            }
        })
        val adSuccess = loadAd(insAd)
        val second = ValueAnimator.ofInt(61, 100)
        second.duration = 1000
        second.addUpdateListener {
            val value = it.animatedValue as Int
            _uiState.update { state ->
                state.copy(progress = value)
            }
        }
        second.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                if (adSuccess != null && insAd.isReady) {
                    insAd.showAd()
                } else {
                    _uiState.update {
                        it.copy(isInitComplete = true)
                    }
                }
            }
        })
        first.end()
        second.start()
    }


    private suspend fun loadAd(insAd: MaxInterstitialAd): Boolean? {
        return withTimeoutOrNull(10000) {
            insAd.loadAd()
            while (!insAd.isReady) {
                delay(500)
            }
            true
        }
    }

}