package com.zjf.clear.ui.activity

import android.graphics.drawable.AnimationDrawable
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseActivity
import com.zjf.clear.data.Constant
import com.zjf.clear.databinding.ActivityBatteryBinding
import com.zjf.clear.launchResultActivity
import com.zjf.clear.ui.viewmodel.PhoneBoosterViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/24
 */
class BatteryActivity : BaseActivity<ActivityBatteryBinding, PhoneBoosterViewModel>() {

    override val mViewModel: PhoneBoosterViewModel by viewModels()


    override fun setupView() {

        val drawable = binding.ivBattery.drawable as AnimationDrawable
        drawable.start()

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun setupEvent() {

    }

    override fun setupData() {

        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect {
                if (it.isComplete) {
                    launchResultActivity(Constant.ID_BATTERY)
                }
            }
        }

    }


}