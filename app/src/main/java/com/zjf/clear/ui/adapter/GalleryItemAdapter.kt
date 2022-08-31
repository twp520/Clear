package com.zjf.clear.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjf.clear.R
import com.zjf.clear.ui.state.GalleryItemUiState

/**
 * create by colin
 * 2022/8/31
 */
class GalleryItemAdapter :
    BaseQuickAdapter<GalleryItemUiState, BaseViewHolder>(R.layout.item_gallery_item) {

    override fun convert(holder: BaseViewHolder, item: GalleryItemUiState) {
        Glide.with(context).load(item.uri).into(holder.getView(R.id.item_gallery_image))
        holder.setImageResource(
            R.id.item_gallery_ckb,
            if (item.isChecked) R.drawable.icon_ckb_checked else R.drawable.icon_ckb_normal
        )
    }
}