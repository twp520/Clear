package com.zjf.clear.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.activity.viewModels
import com.beeline.common.BaseActivity
import com.beeline.common.launchActivity
import com.blankj.utilcode.util.LogUtils
import com.zjf.clear.R
import com.zjf.clear.data.Constant
import com.zjf.clear.data.ad.AdUtils
import com.zjf.clear.databinding.ActivityMainBinding
import com.zjf.clear.service.ToolService
import com.zjf.clear.ui.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), View.OnClickListener {

    override val mViewModel: MainViewModel by viewModels()

    override fun setupView() {

        AdUtils.setNativeAdClickEnable(binding.adContainer)

        AdUtils.adClickEvent.observe(this) {
            AdUtils.setNativeAdClickEnable(binding.adContainer)
        }


    }

    override fun setupEvent() {
        binding.btnBooster.setOnClickListener(this)
        binding.btnBattery.setOnClickListener(this)
        binding.btnCpu.setOnClickListener(this)
        binding.btnGallery.setOnClickListener(this)
        binding.homeClanBord.setOnClickListener(this)

        handleJump(intent)
    }

    override fun setupData() {
        startService()
        mViewModel.initMainAd(this, binding.adContainer)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleJump(intent)
    }

    override fun onResume() {
        super.onResume()
        binding.homeClanBord.post {
            startCleanAnimated()
        }
        mViewModel.resumeAd()
    }

    override fun onPause() {
        super.onPause()
        binding.homeClanBord.animation?.cancel()
        binding.tvClean.animation?.cancel()
    }

    override fun onClick(v: View) {
        mViewModel.showMainInterstitialAd(v.id, R.id.home_clan_bord) {
            when (it) {
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
                R.id.btn_gallery -> {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        launchActivity(GalleryActivity::class.java)
                    } else {
                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
                    }
                }
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

    private fun startService() {
        val intent = Intent(this, ToolService::class.java)
        startForegroundService(intent)
    }

    private fun handleJump(intent: Intent?) {
        intent ?: return
        val funcId = intent.getIntExtra("funcId", -1)
        if (funcId < 0) return
        intent.removeExtra("funcId")
        when (funcId) {
            Constant.ID_CLEAN -> {
                binding.homeClanBord.performClick()
            }
            Constant.ID_BOOSTER -> {
                binding.btnBooster.performClick()
            }
            Constant.ID_BATTERY -> {
                binding.btnBattery.performClick()
            }
            Constant.ID_CPU -> {
                binding.btnCpu.performClick()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            launchActivity(GalleryActivity::class.java)
        } else {
            LogUtils.d("Not permission")
        }
    }

}