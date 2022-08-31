package com.zjf.clear.ui.state

import android.net.Uri

/**
 * create by colin
 * 2022/8/29
 */
data class GalleryAlbumItemState(
    val name: String,
    val size: String,
    val count: Int,
    val picture: List<Uri>,
    val isScreenshot: Boolean = false
)