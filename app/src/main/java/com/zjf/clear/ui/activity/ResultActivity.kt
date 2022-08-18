package com.zjf.clear.ui.activity

import android.graphics.Color
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.beeline.common.BaseActivity
import com.beeline.common.launchActivity
import com.blankj.utilcode.util.BarUtils
import com.zjf.clear.R
import com.zjf.clear.databinding.ActivityResultBinding
import com.zjf.clear.ui.adapter.ResultAdapter
import com.zjf.clear.ui.viewmodel.ResultViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/14
 */
class ResultActivity : BaseActivity<ActivityResultBinding, ResultViewModel>() {

    override val mViewModel: ResultViewModel by viewModels()

    private val mAdapter = ResultAdapter()

    override fun setupView() {

        binding.resultRv.layoutManager = LinearLayoutManager(this)
        binding.resultRv.adapter = mAdapter

        val header = layoutInflater.inflate(R.layout.header_result, binding.resultRv, false)
        mAdapter.addHeaderView(header)

        BarUtils.setStatusBarColor(this, Color.parseColor("#EBF5FE"))
        BarUtils.setStatusBarLightMode(this, true)
    }

    override fun setupEvent() {

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        mAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.item_result_button) {
                launchActivity(mAdapter.getItem(position).jumpClass, finish = true)
            }
        }

    }

    override fun setupData() {
        val funcId = intent.getIntExtra("funcId", -1)
        mViewModel.fetchData(funcId)

        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect {
                binding.toolbar.title = it.title
                mAdapter.setNewInstance(it.listData.toMutableList())
            }
        }
    }

}