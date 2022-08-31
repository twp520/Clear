package com.zjf.clear.ui.activity

import android.animation.ValueAnimator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseActivity
import com.zjf.clear.data.Constant
import com.zjf.clear.databinding.ActivityCleanBinding
import com.zjf.clear.launchResultActivity
import com.zjf.clear.ui.viewmodel.CleanViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/15
 */
class CleanActivity : BaseActivity<ActivityCleanBinding, CleanViewModel>() {

    override val mViewModel: CleanViewModel by viewModels()

    override fun setupView() {

        binding.view1.post {
            startImageAnimated()
        }

    }

    override fun setupEvent() {

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun setupData() {

        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect {
                if (it.isComplete) {
                    launchResultActivity(Constant.ID_CLEAN)
                } else {
                    binding.tvUnit.text = it.unit
                    startTextAnimated(it.size)
                }
            }
        }
    }

    private fun startImageAnimated() {
        fun setupRotate(rotate: RotateAnimation) {
            rotate.duration = 1500
            rotate.repeatCount = -1
            rotate.repeatMode = Animation.RESTART
            rotate.interpolator = LinearInterpolator()
        }

        val rotate1 = RotateAnimation(
            0f,
            -360f, binding.view1.pivotX, binding.view1.pivotY
        ).also { setupRotate(it) }
        val rotate3 = RotateAnimation(
            0f,
            360f, binding.view3.pivotX, binding.view3.pivotY
        ).also { setupRotate(it) }

        binding.view1.startAnimation(rotate1)
        binding.view3.startAnimation(rotate3)
    }

    private fun startTextAnimated(size: Float) {
        val animate = ValueAnimator.ofFloat(0f, size)
        animate.duration = 5000
        animate.addUpdateListener {
            binding.tvSize.text = String.format("%.1f", it.animatedValue)
        }
        animate.startDelay = 150
        animate.start()
    }
}