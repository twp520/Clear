package com.zjf.clear.data.ad

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.zjf.clear.ui.widget.NativeAdContainer
import java.text.DateFormat

/**
 * create by colin
 * 2022/2/21
 */
object AdUtils {

    private const val EVENT_LOAD_SUCCESS = "ad_load_success"
    private const val EVENT_LOAD_FAIL = "ad_load_fail"

    private const val KEY_NETWORK = "network"
    private const val KEY_UNIT = "unit"


    private const val KEY_CODE = "code"
    private const val KEY_MSG = "msg"

    private const val CONFIG_KEY_AD_CLICK_COUNT = "NativeClickCount"
    private const val CONFIG_KEY_AD_SAFE_MODEL = "SafeModel"
    const val SP_KEY_AD_CLICK_DATE = "ad_click_date"
    const val SP_KEY_AD_CLICK_COUNT = "ad_click_count"

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private val unitMap = ArrayMap<String, String>()

    fun init(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }


    fun logSuccess(maxAd: MaxAd) {
        val args = Bundle()
        args.putString(KEY_NETWORK, maxAd.networkName)
        args.putString(KEY_UNIT, unitMap.getOrDefault(maxAd.adUnitId, "UnKnow"))
        firebaseAnalytics.logEvent(EVENT_LOAD_SUCCESS, args)
        LogUtils.d(args)
    }

    fun logFail(location: String, maxError: MaxError) {
        val args = Bundle()
        args.putString(KEY_UNIT, unitMap.getOrDefault(location, "UnKnow"))
        args.putInt(KEY_CODE, maxError.code)
        args.putString(KEY_MSG, maxError.message)
        firebaseAnalytics.logEvent(EVENT_LOAD_FAIL, args)
        LogUtils.d(args)
    }

    val adClickEvent = MutableLiveData<Int>()

    fun setNativeAdClickEnable(container: NativeAdContainer) {
        val today = TimeUtils.getNowString(DateFormat.getDateInstance())
        val lastDay = SPUtils.getInstance().getString(SP_KEY_AD_CLICK_DATE, "")
        var lastCount = SPUtils.getInstance().getInt(SP_KEY_AD_CLICK_COUNT, 0)
        if (!TextUtils.equals(today, lastDay)) {
            lastCount = 0
            SPUtils.getInstance().put(SP_KEY_AD_CLICK_COUNT, 0)
        }
        val configCount = Firebase.remoteConfig.getLong(CONFIG_KEY_AD_CLICK_COUNT)
        container.needIntercept = lastCount >= configCount

        LogUtils.d(lastDay, today, lastCount, configCount)
    }

    fun isSafeAdModel(): Boolean {
        return Firebase.remoteConfig.getBoolean(CONFIG_KEY_AD_SAFE_MODEL)
    }

}