package com.zjf.clear.data

import android.content.Context
import com.zjf.clear.R
import com.zjf.clear.ui.activity.BatteryActivity
import com.zjf.clear.ui.activity.CleanActivity
import com.zjf.clear.ui.activity.PhoneBoosterAct
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
                context.getString(R.string.clean),
                context.getString(R.string.clean),
                CleanActivity::class.java
            ),
            ResultItemUiState(
                Constant.ID_BOOSTER,
                R.drawable.icon_item_result_booster,
                context.getString(R.string.phone_booster),
                context.getString(R.string.phone_booster),
                context.getString(R.string.phone_booster),
                PhoneBoosterAct::class.java
            ),
            ResultItemUiState(
                Constant.ID_CPU,
                R.drawable.icon_item_result_cpu,
                context.getString(R.string.cpu_cooler),
                context.getString(R.string.cpu_cooler),
                context.getString(R.string.cpu_cooler),
                CleanActivity::class.java
            ), ResultItemUiState(
                Constant.ID_BATTERY,
                R.drawable.icon_item_result_battery,
                context.getString(R.string.battery),
                context.getString(R.string.battery),
                context.getString(R.string.battery),
                BatteryActivity::class.java
            ), ResultItemUiState(
                Constant.ID_GALLERY,
                R.drawable.icon_item_result_gallery,
                context.getString(R.string.photo_clean),
                context.getString(R.string.photo_clean),
                context.getString(R.string.photo_clean),
                CleanActivity::class.java
            )
        )
    }
}