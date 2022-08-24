package com.zjf.clear

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.beeline.common.launchActivity
import com.zjf.clear.ui.activity.ResultActivity

/**
 * create by colin
 * 2022/8/22
 */


fun AppCompatActivity.launchResultActivity(funcId: Int) {
    launchActivity(
        ResultActivity::class.java,
        bundleOf(Pair("funcId", funcId)),
        true
    )
}