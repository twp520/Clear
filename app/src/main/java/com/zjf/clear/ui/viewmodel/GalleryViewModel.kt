package com.zjf.clear.ui.viewmodel

import android.app.Application
import com.beeline.common.BaseViewModel
import com.zjf.clear.data.usecase.GalleryUseCase
import com.zjf.clear.ui.state.GalleryItemUiState
import com.zjf.clear.ui.state.GalleryUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

/**
 * create by colin
 * 2022/8/27
 */
class GalleryViewModel(app: Application) : BaseViewModel(app) {

    private val useCase = GalleryUseCase()

    private val _galleryState = MutableStateFlow(GalleryUiState(0, false))
    val galleryState = _galleryState.asStateFlow()

    private val checkedPicture = mutableListOf<GalleryItemUiState>()
    val galleryCheckedSize = MutableStateFlow("")
    val galleryDeleteSize = MutableStateFlow("")

    fun loadPicture() {

        launchIO {
            val contentResolver = getApplication<Application>().contentResolver
            useCase.loadGalleryData(contentResolver)
            delay(1000)
            withContext(Dispatchers.Main) {
                _galleryState.update {
                    it.copy(step = 1, jumpIsScreenshot = false)
                }
            }
        }
    }

    fun jumpResult(screenshot: Boolean) {
        _galleryState.update {
            it.copy(step = 2, jumpIsScreenshot = screenshot)
        }
    }

    fun updateChecked(item: GalleryItemUiState) {
        if (checkedPicture.contains(item)) {
            checkedPicture.remove(item)
        } else {
            checkedPicture.add(item)
        }
        if (checkedPicture.isEmpty()) {
            galleryCheckedSize.update { "" }
        } else {
            galleryCheckedSize.update {
                useCase.sumPictureSize(checkedPicture)
            }
        }
    }

    fun jumpDelete() {
        _galleryState.update {
            it.copy(step = 3)
        }
    }

    fun deletePicture() {
        launchIO {
            delay(1000)
            val contentResolver = getApplication<Application>().contentResolver
            val copy = checkedPicture.map { it }
            copy.forEachIndexed { index, item ->
                useCase.deleteGalleryData(contentResolver, item.uri)
                checkedPicture.removeAt(index)
                delay(100)
                galleryDeleteSize.update {
                    useCase.sumPictureSize(checkedPicture)
                }
            }
            checkedPicture.clear()
            _galleryState.update {
                it.copy(step = 5)
            }
        }
    }

    fun getAlbumData() = useCase.albumData

    fun getPictureData() =
        if (galleryState.value.jumpIsScreenshot) useCase.screenShotData else useCase.pictureShotData

    fun getCheckedPictureUris() = checkedPicture.map { it.uri }
}