package com.zjf.clear.ui.adapter

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjf.clear.R
import com.zjf.clear.ui.state.ResultItemUiState

/**
 * create by colin
 * 2022/8/15
 */
class ResultAdapter : BaseQuickAdapter<ResultItemUiState, BaseViewHolder>(R.layout.item_result) {

    override fun convert(holder: BaseViewHolder, item: ResultItemUiState) {

        holder.setImageResource(R.id.item_result_icon, item.icon)
        holder.setText(R.id.item_result_name, item.displayName)
        holder.setText(R.id.item_result_desc, item.descriptor)
        holder.setText(R.id.item_result_button, item.buttonText)
        holder.getView<View>(R.id.item_result_button).setOnClickListener {
           getOnItemChildClickListener()?.onItemChildClick(this,it,holder.adapterPosition-headerLayoutCount)
        }

    }
}