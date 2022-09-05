package com.zjf.clear.data.repository

import android.content.Context
import com.zjf.clear.R
import com.zjf.clear.data.Constant
import com.zjf.clear.ui.activity.*
import com.zjf.clear.ui.state.ResultItemUiState

/**
 * create by colin
 * 2022/8/15
 */
class ResultRepository {

    fun getAllFuncList(context: Context): MutableList<ResultItemUiState> {

        return mutableListOf(
            ResultItemUiState(
                Constant.ID_CLEAN,
                R.drawable.icon_item_result_clean,
                context.getString(R.string.clean),
                "Clean up vour phone memorv!",
                "CLEAN NOW",
                CleanActivity::class.java
            ),
            ResultItemUiState(
                Constant.ID_BOOSTER,
                R.drawable.icon_item_result_booster,
                context.getString(R.string.phone_booster),
                "The mobile phone runs slowly, click to accelerate!",
                "OPTIMIZE",
                PhoneBoosterAct::class.java
            ),
            ResultItemUiState(
                Constant.ID_CPU,
                R.drawable.icon_item_result_cpu,
                context.getString(R.string.cpu_cooler),
                "Phone getting hot? Try one-click cooling!",
                "COOL DOWN",
                CPUActivity::class.java
            ), ResultItemUiState(
                Constant.ID_BATTERY,
                R.drawable.icon_item_result_battery,
                context.getString(R.string.battery),
                "Some apps are consuming battery now, Click to extend longer battery life.",
                "OPTIMIZE",
                BatteryActivity::class.java
            ), ResultItemUiState(
                Constant.ID_GALLERY,
                R.drawable.icon_item_result_gallery,
                context.getString(R.string.photo_clean),
              "Go and see the photos hidden in vour phone that vou don't know about!",
                "LET GO",
                GalleryActivity::class.java
            )
        )
    }
}