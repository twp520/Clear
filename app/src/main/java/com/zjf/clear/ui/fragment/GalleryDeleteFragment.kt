package com.zjf.clear.ui.fragment

import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseFragment
import com.zjf.clear.databinding.FragmentGalleryDeleteBinding
import com.zjf.clear.ui.viewmodel.GalleryViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/31
 */
class GalleryDeleteFragment : BaseFragment<FragmentGalleryDeleteBinding, GalleryViewModel>() {

    override val viewModel: GalleryViewModel by activityViewModels()

    override fun createBinding(inflater: LayoutInflater): FragmentGalleryDeleteBinding {
        return FragmentGalleryDeleteBinding.inflate(inflater)
    }

    override fun setupView() {

        binding.image.post {
            val rotate = RotateAnimation(
                0f, 360f,
                binding.image.pivotX, binding.image.pivotY
            ).apply {
                duration = 1000
                repeatCount = Animation.INFINITE
                repeatMode = Animation.RESTART
                interpolator = LinearInterpolator()
            }
            binding.image.startAnimation(rotate)
        }

    }

    override fun setupEvent() {

    }

    override fun setupData() {

        viewModel.deletePicture()

        lifecycleScope.launchWhenStarted {
            viewModel.galleryDeleteSize.collect {
                binding.text.text = it
            }
        }

    }


}