package com.zjf.clear.ui.activity

import androidx.activity.viewModels
import com.beeline.common.BaseActivity
import com.zjf.clear.databinding.ActivityResultBinding
import com.zjf.clear.ui.viewmodel.SimpleAdViewModel

/**
 * create by colin
 * 2022/8/14
 */
class ResultActivity:BaseActivity<ActivityResultBinding,SimpleAdViewModel>() {

    override val mViewModel: SimpleAdViewModel by viewModels()

    override fun setupView() {
    }

    override fun setupEvent() {
    }

    override fun setupData() {
    }

}