package com.zjf.clear.ui.fragment

import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.beeline.common.BaseFragment
import com.zjf.clear.databinding.FragmentGalleryScanBinding
import com.zjf.clear.ui.viewmodel.GalleryViewModel
import kotlinx.coroutines.delay

/**
 * create by colin
 * 2022/8/27
 */
class GalleryScanFragment : BaseFragment<FragmentGalleryScanBinding, GalleryViewModel>() {

    override val viewModel: GalleryViewModel by activityViewModels()

    override fun createBinding(inflater: LayoutInflater): FragmentGalleryScanBinding {
        return FragmentGalleryScanBinding.inflate(inflater)
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

        lifecycleScope.launchWhenResumed {
            val end = arrayOf(".", "..", "...")
            repeat(Int.MAX_VALUE) {
                binding.text.text = end[it % 3]
                delay(200)
            }
        }
    }

    override fun setupEvent() {

    }

    override fun setupData() {

        viewModel.loadPicture()
    }
}