package com.zjf.clear.data.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.beeline.common.BaseRepository
import com.zjf.clear.ui.state.GalleryItemUiState

/**
 * create by colin
 * 2022/8/24
 */
class GalleryRepository : BaseRepository() {

    private val screenShot = setOf("Screen_Shot", "ScreenShot", "screen_shot","Screenshots")

    fun queryPictureScreenShot(contentResolver: ContentResolver) =
        queryPictureByAlbumFilter(contentResolver, screenShot, true)

    fun queryPictureNotScreenShot(contentResolver: ContentResolver) =
        queryPictureByAlbumFilter(contentResolver, screenShot, false)

    /**
     * 根据相册条件查询
     *
     * @param key 条件集合
     * @param contain 包含或不包含
     */
    private fun queryPictureByAlbumFilter(
        contentResolver: ContentResolver,
        key: Set<String>,
        contain: Boolean
    ): MutableList<GalleryItemUiState> {

        val videoList = mutableListOf<GalleryItemUiState>()
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )
        val where = if (contain) "IN" else "NOT IN";
        val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} $where (" +  Array(key.size) { "?" }.joinToString() + ")"

        // Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"

        val query = contentResolver.query(
            collection,
            projection,
            selection,
            key.toTypedArray(),
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val albumColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                val album = cursor.getString(albumColumn) ?: "Null"

                val contentUri: Uri = ContentUris.withAppendedId(collection, id)
                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videoList += GalleryItemUiState(contentUri, size, album, name)
            }
        }
        return videoList
    }
}