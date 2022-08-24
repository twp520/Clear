package com.zjf.clear.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.activity.viewModels
import androidx.core.animation.addListener
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseActivity
import com.zjf.clear.data.Constant
import com.zjf.clear.databinding.ActivityPhoneBoosterBinding
import com.zjf.clear.launchResultActivity
import com.zjf.clear.ui.viewmodel.PhoneBoosterViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/14
 */
class PhoneBoosterAct : BaseActivity<ActivityPhoneBoosterBinding, PhoneBoosterViewModel>() {

    override val mViewModel: PhoneBoosterViewModel by viewModels()

    private var mSet: AnimatorSet? = null

    override fun setupView() {

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.rocket.post {
            startRocketAnimated()
        }
    }

    override fun setupEvent() {


    }

    override fun setupData() {

        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect {
                if (it.isComplete)
                    launchResultActivity(Constant.ID_BOOSTER)
            }
        }

    }


    private fun startRocketAnimated() {

        val rotate = ObjectAnimator.ofFloat(
            binding.rocket as View, "rotation",
            0f, 15f, -15f, 0f
        )
        rotate.interpolator = LinearInterpolator()
        val translate = ObjectAnimator.ofFloat(
            binding.rocket as View, "translationY",
            0f, -400f
        )
        translate.interpolator = AccelerateInterpolator()
        val set = AnimatorSet()
        set.playSequentially(rotate, translate)
        set.duration = 1000
        set.addListener(onEnd = {
            it.start()
        })
        set.start()
        mSet = set
    }

    override fun onDestroy() {
        super.onDestroy()
        mSet?.cancel()
    }
}