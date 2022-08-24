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
import com.zjf.clear.ui.viewmodel.PhoneBoosterViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/24
 */
class CPUActivity : BaseActivity<ActivityCpuBinding, PhoneBoosterViewModel>() {

    override val mViewModel: PhoneBoosterViewModel by viewModels()

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

    }

    override fun setupData() {

        lifecycleScope.launchWhenStarted {

            mViewModel.uiState.collect {
                if (it.isComplete)
                    launchResultActivity(Constant.ID_CPU)
            }
        }
    }


}