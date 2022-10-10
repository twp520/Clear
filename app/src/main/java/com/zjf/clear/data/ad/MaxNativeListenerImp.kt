package com.zjf.clear.data.ad

import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import java.text.DateFormat

/**
 * create by colin
 * 2022/2/21
 */
open class MaxNativeListenerImp : MaxNativeAdListener() {

    override fun onNativeAdLoaded(maxAdView: MaxNativeAdView, maxAd: MaxAd) {
        AdUtils.logSuccess(maxAd)
    }

    override fun onNativeAdLoadFailed(adUnitId: String, p1: MaxError) {
        AdUtils.logFail(adUnitId, p1)
    }

    override fun onNativeAdClicked(p0: MaxAd?) {
        val lastCount = SPUtils.getInstance().getInt(AdUtils.SP_KEY_AD_CLICK_COUNT, 0)
        SPUtils.getInstance().put(AdUtils.SP_KEY_AD_CLICK_COUNT, lastCount + 1)
        val today = TimeUtils.getNowString(DateFormat.getDateInstance())
        SPUtils.getInstance().put(AdUtils.SP_KEY_AD_CLICK_DATE, today)
        AdUtils.adClickEvent.postValue((AdUtils.adClickEvent.value ?: 0) + 1)
    }
}