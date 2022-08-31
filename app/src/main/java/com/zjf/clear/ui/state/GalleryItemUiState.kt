package com.zjf.clear.ui.state

import android.net.Uri

/**
 * create by colin
 * 2022/8/24
 */
data class GalleryItemUiState(
    val uri: Uri,
    val size: Int,
    val album: String,
    val name: String,
    var isChecked: Boolean = false
)