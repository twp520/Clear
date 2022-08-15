package com.zjf.clear.ui.activity

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseActivity
import com.beeline.common.launchActivity
import com.zjf.clear.databinding.ActivityWelcomeBinding
import com.zjf.clear.ui.viewmodel.WelcomeViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/11
 */
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding, WelcomeViewModel>() {

    override val mViewModel: WelcomeViewModel by viewModels()

    override fun setupView() {

    }

    override fun setupEvent() {

        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect {
                if (it.isInitComplete)
                    launchActivity(MainActivity::class.java)
            }
        }

    }

    override fun setupData() {

    }
}