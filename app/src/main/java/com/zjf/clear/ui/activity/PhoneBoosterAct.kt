package com.zjf.clear.ui.activity

import androidx.activity.viewModels
import com.beeline.common.BaseActivity
import com.zjf.clear.databinding.ActivityPhoneBoosterBinding
import com.zjf.clear.ui.viewmodel.PhoneBoosterViewModel

/**
 * create by colin
 * 2022/8/14
 */
class PhoneBoosterAct : BaseActivity<ActivityPhoneBoosterBinding, PhoneBoosterViewModel>() {

    override val mViewModel: PhoneBoosterViewModel by viewModels()

    override fun setupView() {


        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun setupEvent() {


    }

    override fun setupData() {

    }

}