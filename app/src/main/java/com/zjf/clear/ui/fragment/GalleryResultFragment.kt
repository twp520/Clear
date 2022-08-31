package com.zjf.clear.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.beeline.common.BaseFragment
import com.zjf.clear.data.Constant
import com.zjf.clear.databinding.FragmentGalleryResultBinding
import com.zjf.clear.launchResultActivity
import com.zjf.clear.ui.activity.GalleryActivity
import com.zjf.clear.ui.adapter.GalleryItemAdapter
import com.zjf.clear.ui.viewmodel.GalleryViewModel
import kotlinx.coroutines.flow.collect

/**
 * create by colin
 * 2022/8/27
 */
class GalleryResultFragment : BaseFragment<FragmentGalleryResultBinding, GalleryViewModel>() {

    override val viewModel: GalleryViewModel by activityViewModels()

    private var requestPermission: ActivityResultLauncher<String>? = null
    private var requestIntent: ActivityResultLauncher<IntentSenderRequest>? = null

    override fun createBinding(inflater: LayoutInflater): FragmentGalleryResultBinding {
        return FragmentGalleryResultBinding.inflate(inflater)
    }

    override fun setupView() {

        val adapter = GalleryItemAdapter()
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.imageList.layoutManager = layoutManager
        binding.imageList.adapter = adapter
        adapter.setNewInstance(viewModel.getPictureData())


        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position)
            item.isChecked = !item.isChecked
            adapter.notifyItemChanged(position)
            viewModel.updateChecked(item)
        }

        requestPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it == true) {
                viewModel.jumpDelete()
            }
        }

        requestIntent =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val galleryActivity = requireActivity() as GalleryActivity
                    galleryActivity.launchResultActivity(Constant.ID_GALLERY)
                }
            }
    }

    override fun setupEvent() {

        binding.btnDelete.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                alertPermissionDialog()
            } else {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    viewModel.jumpDelete()
                } else {
                    requestPermission?.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }

    override fun setupData() {

        lifecycleScope.launchWhenStarted {
            viewModel.galleryCheckedSize.collect {
                binding.btnDelete.text = "Delete $it"
            }
        }
    }

    private fun alertPermissionDialog() {
        val intent = MediaStore.createDeleteRequest(
            requireContext().contentResolver,
            viewModel.getCheckedPictureUris()
        )
        requestIntent?.launch(IntentSenderRequest.Builder(intent).build())
    }

}