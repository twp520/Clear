package com.zjf.clear.ui.listener

import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.beeline.common.INoProguard

/**
 * create by colin
 * 2022/2/15
 */
open class MaxAdListenerImp : MaxAdListener,INoProguard {

    override fun onAdLoaded(ad: MaxAd) {
    }

    override fun onAdDisplayed(ad: MaxAd) {
    }

    override fun onAdHidden(ad: MaxAd) {
    }

    override fun onAdClicked(ad: MaxAd) {
    }

    override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
    }

    override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
    }
}