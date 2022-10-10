package com.zjf.clear.ui.activity

import android.graphics.drawable.AnimationDrawable
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseActivity
import com.zjf.clear.R
import com.zjf.clear.data.Constant
import com.zjf.clear.databinding.ActivityBatteryBinding
import com.zjf.clear.launchResultActivity
import com.zjf.clear.ui.viewmodel.SimpleCompleteViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/24
 */
class BatteryActivity : BaseActivity<ActivityBatteryBinding, SimpleCompleteViewModel>() {

    override val mViewModel: SimpleCompleteViewModel by viewModels()


    override fun setupView() {

        val drawable = binding.ivBattery.drawable as AnimationDrawable
        drawable.start()

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun setupEvent() {

        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect {
                if (it.isComplete) {
                    launchResultActivity(Constant.ID_BATTERY)
                }
            }
        }

    }

    override fun setupData() {

        mViewModel.loadWaitAd(this, getString(R.string.ad_unit_common_insert))
    }


}