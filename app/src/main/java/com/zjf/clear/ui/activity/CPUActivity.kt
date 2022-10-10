package com.zjf.clear.ui.activity

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseActivity
import com.zjf.clear.R
import com.zjf.clear.data.Constant
import com.zjf.clear.databinding.ActivityCpuBinding
import com.zjf.clear.launchResultActivity
import com.zjf.clear.ui.viewmodel.SimpleCompleteViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/24
 */
class CPUActivity : BaseActivity<ActivityCpuBinding, SimpleCompleteViewModel>() {

    override val mViewModel: SimpleCompleteViewModel by viewModels()

    override fun setupView() {

        val scannerAnimated = AnimationUtils.loadAnimation(this, R.anim.cpu_scanner_animated)
        scannerAnimated.duration = 1000
        scannerAnimated.repeatCount = Animation.INFINITE
        scannerAnimated.repeatMode = Animation.REVERSE
        binding.ivBatteryScanner.post {
            binding.ivBatteryScanner.startAnimation(scannerAnimated)
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun setupEvent() {

        lifecycleScope.launchWhenStarted {

            mViewModel.uiState.collect {
                if (it.isComplete)
                    launchResultActivity(Constant.ID_CPU)
            }
        }

    }

    override fun setupData() {

        mViewModel.loadWaitAd(this, getString(R.string.ad_unit_common_insert))
    }


}