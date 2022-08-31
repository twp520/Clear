package com.zjf.clear.data.usecase

import android.content.ContentResolver
import android.net.Uri
import com.zjf.clear.data.repository.GalleryRepository
import com.zjf.clear.ui.state.GalleryAlbumItemState
import com.zjf.clear.ui.state.GalleryItemUiState
import kotlin.math.min

/**
 * create by colin
 * 2022/8/30
 */
class GalleryUseCase {

    private val repository = GalleryRepository()

    val albumData = mutableListOf<GalleryAlbumItemState>()
    val screenShotData = mutableListOf<GalleryItemUiState>()
    val pictureShotData = mutableListOf<GalleryItemUiState>()

    fun loadGalleryData(contentResolver: ContentResolver) {
        val screenShot = repository.queryPictureScreenShot(contentResolver)
        val otherPicture = repository.queryPictureNotScreenShot(contentResolver)
        albumData.clear()
        if (screenShot.isNotEmpty()) {
            screenShotData.clear()
            screenShotData.addAll(screenShot)
            albumData.add(transformAlbumByPictureList("ScreenShot", screenShot))
        }
        if (otherPicture.isNotEmpty()) {
            pictureShotData.clear()
            pictureShotData.addAll(otherPicture)
            albumData.add(transformAlbumByPictureList("Picture", otherPicture))
        }
    }

    fun deleteGalleryData(contentResolver: ContentResolver, uri: Uri) {
        contentResolver.delete(uri, null, null)
    }

    private fun transformAlbumByPictureList(
        name: String,
        pictures: List<GalleryItemUiState>
    ): GalleryAlbumItemState {
        return GalleryAlbumItemState(
            name, sumPictureSize(pictures), pictures.size,
            pictures.subList(0, min(pictures.size, 4)).map { it.uri },
            isScreenshot = name == "ScreenShot"
        )
    }

    fun sumPictureSize(pictures: List<GalleryItemUiState>): String {
        val unit: String
        var sumSize = pictures.sumOf { it.size.toDouble() }
        val gbUnit = 1024.00 * 1024.00 * 1024.00
        val mbUnit = 1024.00 * 1024
        val kbUnit = 1024.00
        sumSize /= if (sumSize > gbUnit) {
            unit = "GB"
            gbUnit
        } else if (sumSize > mbUnit) {
            unit = "MB"
            mbUnit
        } else {
            unit = "KB"
            kbUnit
        }
        return String.format("%.2f${unit}", sumSize)
    }
}