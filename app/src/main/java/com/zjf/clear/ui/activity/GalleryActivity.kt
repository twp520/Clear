package com.zjf.clear.ui.activity

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseActivity
import com.zjf.clear.R
import com.zjf.clear.data.Constant
import com.zjf.clear.databinding.ActivityGalleryBinding
import com.zjf.clear.launchResultActivity
import com.zjf.clear.ui.fragment.GalleryAlbumFragment
import com.zjf.clear.ui.fragment.GalleryDeleteFragment
import com.zjf.clear.ui.fragment.GalleryResultFragment
import com.zjf.clear.ui.fragment.GalleryScanFragment
import com.zjf.clear.ui.viewmodel.GalleryViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/27
 */
class GalleryActivity : BaseActivity<ActivityGalleryBinding, GalleryViewModel>() {

    override val mViewModel: GalleryViewModel by viewModels()

    override fun setupView() {

    }

    override fun setupEvent() {

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun setupData() {

        lifecycleScope.launchWhenCreated {
            mViewModel.galleryState.collect {
                val fragment = when (it.step) {
                    0 -> {
                        GalleryScanFragment()
                    }
                    1 -> {
                        GalleryAlbumFragment()
                    }
                    2 -> {
                        GalleryResultFragment()
                    }
                    3 -> {
                        GalleryDeleteFragment()
                    }
                    else -> {
                        null
                    }
                }
                if (fragment == null) {
                    launchResultActivity(Constant.ID_GALLERY)
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.gallery_container, fragment, fragment.javaClass.name)
                        .commit()
                }

            }
        }
    }

}