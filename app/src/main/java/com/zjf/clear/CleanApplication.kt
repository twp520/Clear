package com.zjf.clear

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.zjf.clear.data.ad.AdUtils

/**
 * create by colin
 * 2022/9/14
 */
class CleanApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AdUtils.init(this)
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        Firebase.remoteConfig.setConfigSettingsAsync(configSettings)
        Firebase.remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
            LogUtils.d("远程配置刷新：${it.isSuccessful}")
        }
    }
}