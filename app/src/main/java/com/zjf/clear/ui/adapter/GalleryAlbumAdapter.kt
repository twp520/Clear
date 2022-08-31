package com.zjf.clear.ui.adapter

import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjf.clear.R
import com.zjf.clear.ui.state.GalleryAlbumItemState

/**
 * create by colin
 * 2022/8/30
 */
class GalleryAlbumAdapter :
    BaseQuickAdapter<GalleryAlbumItemState, BaseViewHolder>(R.layout.item_gallery_album) {

    override fun convert(holder: BaseViewHolder, item: GalleryAlbumItemState) {

        holder.setText(R.id.item_album_name, "${item.name} (${item.count})")
        holder.setText(R.id.item_album_size, "${item.size}   >")
        // holder.setText(R.id.item_album_count, "${item.count}")
        val image = holder.getView<LinearLayout>(R.id.item_album_image)
        item.picture.forEachIndexed { index, uri ->
            Glide.with(context)
                .load(uri)
                .optionalCenterCrop()
                .into(image.getChildAt(index * 2 + 1) as ImageView)
        }
    }
}