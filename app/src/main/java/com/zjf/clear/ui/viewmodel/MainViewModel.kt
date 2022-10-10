package com.zjf.clear.ui.viewmodel

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.zjf.clear.R
import com.zjf.clear.data.ad.AdUtils
import com.zjf.clear.ui.state.MainState
import com.zjf.clear.ui.widget.NativeAdContainer

/**
 * create by colin
 * 2022/10/9
 */
class MainViewModel(app: Application) : SimpleAdViewModel(app) {

    private val state = MainState()
    private lateinit var nativeAdLoader: MaxNativeAdLoader

    private var maxIntersAd: MaxInterstitialAd? = null

    fun initMainAd(context: FragmentActivity, nativeAdContainer: NativeAdContainer) {

        nativeAdLoader = createNativeLoader(
            context,
            context.getString(R.string.ad_unit_main_native)
        ) { maxAdView, maxAd ->
            state.maxAd = maxAd
            nativeAdContainer.removeAllViews()
            nativeAdContainer.addView(maxAdView)
        }

        maxIntersAd = createInsertAd(context, context.getString(R.string.ad_unit_main_insert)) {
            state.buttonClick?.call(state.buttonId)
        }
        maxIntersAd?.loadAd()

    }


    fun resumeAd() {
        val nowTime = System.currentTimeMillis()
        if (nowTime - state.lastLoadTime < state.timeDelay)
            return
        state.lastLoadTime = nowTime
        nativeAdLoader.loadAd()
    }

    fun showMainInterstitialAd(buttonId: Int, forceAdId: Int, func1: Utils.Func1<Unit, Int>) {
        val nowTime = System.currentTimeMillis()
        val lastTime = state.lastInterShowTime
        state.lastInterShowTime = nowTime

        LogUtils.d("AdUtils.isSafeAdModel() == ${AdUtils.isSafeAdModel()}")

        if (nowTime - lastTime > state.timeDelay &&
            maxIntersAd?.isReady != true && (!AdUtils.isSafeAdModel() || buttonId == forceAdId)
        ) {
            state.buttonId = buttonId
            state.buttonClick = func1
            maxIntersAd?.showAd()
        } else {
            func1.call(buttonId)
        }
    }

    override fun onCleared() {
        maxIntersAd?.destroy()
        state.maxAd?.let {
            nativeAdLoader.destroy(it)
        }
        nativeAdLoader.destroy()
        super.onCleared()
    }
}