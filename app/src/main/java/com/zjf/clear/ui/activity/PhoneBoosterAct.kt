package com.zjf.clear.ui.activity

import android.animation.ValueAnimator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseActivity
import com.zjf.clear.databinding.ActivityPhoneBoosterBinding
import com.zjf.clear.ui.viewmodel.PhoneBoosterViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/14
 */
class PhoneBoosterAct : BaseActivity<ActivityPhoneBoosterBinding, PhoneBoosterViewModel>() {

    override val mViewModel: PhoneBoosterViewModel by viewModels()

    override fun setupView() {

        binding.view1.post {
            startImageAnimated()
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun setupEvent() {

        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect {
                startTextAnimated(it.size)
            }
        }
    }

    override fun setupData() {

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
        val rotate2 = RotateAnimation(
            0f,
            -360f, binding.view2.pivotX, binding.view2.pivotY
        ).also { setupRotate(it) }
        val rotate3 = RotateAnimation(
            0f,
            360f, binding.view3.pivotX, binding.view3.pivotY
        ).also { setupRotate(it) }

        binding.view1.startAnimation(rotate1)
        binding.view2.startAnimation(rotate2)
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