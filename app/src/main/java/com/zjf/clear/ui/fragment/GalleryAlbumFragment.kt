package com.zjf.clear.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beeline.common.BaseFragment
import com.zjf.clear.databinding.FragmentGalleryAlbumBinding
import com.zjf.clear.ui.adapter.GalleryAlbumAdapter
import com.zjf.clear.ui.viewmodel.GalleryViewModel

/**
 * create by colin
 * 2022/8/27
 */
class GalleryAlbumFragment : BaseFragment<FragmentGalleryAlbumBinding, GalleryViewModel>() {

    override val viewModel: GalleryViewModel by activityViewModels()

    override fun createBinding(inflater: LayoutInflater): FragmentGalleryAlbumBinding {
        return FragmentGalleryAlbumBinding.inflate(inflater)
    }

    override fun setupView() {

        val adapter = GalleryAlbumAdapter()
        binding.albumList.layoutManager = LinearLayoutManager(requireContext())
        binding.albumList.adapter = adapter
        adapter.setNewInstance(viewModel.getAlbumData())

        adapter.setOnItemClickListener { _, _, position ->
            //进入所有照片页面
            viewModel.jumpResult(adapter.getItem(position).isScreenshot)
        }
    }

    override fun setupEvent() {

    }

    override fun setupData() {

    }
}