package com.zjf.clear.ui.activity

import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.activity.viewModels
import com.beeline.common.BaseActivity
import com.beeline.common.EmptyViewModel
import com.beeline.common.launchActivity
import com.zjf.clear.R
import com.zjf.clear.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, EmptyViewModel>(), View.OnClickListener {

    override val mViewModel: EmptyViewModel by viewModels()

    override fun setupView() {

    }

    override fun setupEvent() {
        binding.btnBooster.setOnClickListener(this)
        binding.btnBattery.setOnClickListener(this)
        binding.btnCpu.setOnClickListener(this)
        binding.btnGallery.setOnClickListener(this)
        binding.homeClanBord.setOnClickListener(this)
    }

    override fun setupData() {

    }

    override fun onResume() {
        super.onResume()
        binding.homeClanBord.post {
            startCleanAnimated()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.homeClanBord.animation?.cancel()
        binding.tvClean.animation?.cancel()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_booster -> {
                launchActivity(PhoneBoosterAct::class.java)
            }
            R.id.home_clan_bord -> {
                launchActivity(CleanActivity::class.java)
            }
            R.id.btn_battery -> {
                launchActivity(BatteryActivity::class.java)
            }
            R.id.btn_cpu -> {
                launchActivity(CPUActivity::class.java)
            }
        }

    }

    private fun startCleanAnimated() {
        val rotate = RotateAnimation(
            0f,
            180f, binding.homeClanBord.pivotX, binding.homeClanBord.pivotY
        )
        rotate.repeatCount = Animation.INFINITE
        rotate.repeatMode = Animation.REVERSE
        rotate.duration = 1500
        binding.homeClanBord.startAnimation(rotate)

        val scale = ScaleAnimation(
            1f, 1.2f, 1f, 1.2f,
            binding.tvClean.pivotX, binding.tvClean.pivotY
        )
        scale.repeatCount = Animation.INFINITE
        scale.repeatMode = Animation.REVERSE
        scale.duration = 1500
        binding.tvClean.startAnimation(scale)
    }


}