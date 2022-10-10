package com.zjf.clear.ui.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.applovin.mediation.MaxAd
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.beeline.common.BaseViewModel
import com.zjf.clear.data.ad.MaxInsertListenerImp
import com.zjf.clear.data.ad.MaxNativeListenerImp
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

/**
 * create by colin
 * 2022/8/14
 */
open class SimpleAdViewModel(app: Application) : BaseViewModel(app) {


    protected fun createNativeLoader(
        context: Context,
        adUnit: String,
        adLoaded: (maxAdView: MaxNativeAdView, maxAd: MaxAd) -> Unit
    ): MaxNativeAdLoader {
        return MaxNativeAdLoader(adUnit, context).apply {
            setNativeAdListener(object : MaxNativeListenerImp() {
                override fun onNativeAdLoaded(maxAdView: MaxNativeAdView, maxAd: MaxAd) {
                    super.onNativeAdLoaded(maxAdView, maxAd)
                    adLoaded.invoke(maxAdView, maxAd)
                }
            })
        }
    }

    protected fun createInsertAd(
        activity: Activity,
        adUnit: String,
        hideBlock: Runnable
    ): MaxInterstitialAd {
        return MaxInterstitialAd(adUnit, activity).apply {
            setListener(MaxInsertListenerImp(this, viewModelScope, hideBlock))
        }
    }

    protected suspend fun loadAdWithTimeout(
        insAd: MaxInterstitialAd,
        timeout: Long = 20000
    ): Boolean? {
        return withTimeoutOrNull(timeout) {
            insAd.loadAd()
            val time = measureTimeMillis {
                while (!insAd.isReady) {
                    delay(500)
                }
            }
            //最少等5秒
            if (time < 5000L) {
                delay(5000L - time)
            }
            true
        }
    }
}