package com.zjf.clear.ui.listener

import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.beeline.common.INoProguard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.math.pow

/**
 * create by colin
 * 2022/2/15
 */
open class ClickMaxAdListenerImp(
    private val interstitialAd: MaxInterstitialAd?,
    private val coroutineScope: CoroutineScope,
    private val hideCallback: Runnable? = null
) : MaxAdListenerImp(), INoProguard {

    private var retryAttempt = 0

    override fun onAdHidden(ad: MaxAd) {
        interstitialAd?.loadAd()
        hideCallback?.run()
    }

    override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
        retryAttempt++
        val delayMillis: Long = TimeUnit.SECONDS.toMillis(
            2.0.pow(6.coerceAtMost(retryAttempt).toDouble())
                .toLong()
        )
        coroutineScope.launch {
            delay(delayMillis)
            interstitialAd?.loadAd()
        }
    }

    override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
        interstitialAd?.loadAd()
        hideCallback?.run()
    }

    override fun onAdLoaded(ad: MaxAd) {
        super.onAdLoaded(ad)
    }
}