package com.zjf.clear.ui.state

import com.applovin.mediation.MaxAd
import com.blankj.utilcode.util.Utils

/**
 * create by colin
 * 2022/10/9
 */
data class MainState(
    var lastLoadTime: Long = 0L,
    val timeDelay: Long = 11000,
    var lastInterShowTime: Long = 0L,
    var maxAd: MaxAd? = null,
    var buttonId: Int = 0,
    var buttonClick: Utils.Func1<Unit, Int>? = null
)
