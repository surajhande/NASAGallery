package com.suraj.nasagallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.suraj.nasagallery.R
import com.suraj.nasagallery.databinding.FragmentImageViewerBinding

class ImageViewerFragment : Fragment() {

    private lateinit var binding: FragmentImageViewerBinding
    private val viewModel: ImagesViewModel by activityViewModels()
    private var isInfoVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isInfoVisible = savedInstanceState?.getBoolean(KEY_INFO, false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageViewerBinding.inflate(layoutInflater, container, false)
        binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        binding.toolbar.inflateMenu(R.menu.menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.menu_item_download -> {
                    // TODO: Downlaad HD image here.
                }
                R.id.menu_item_info -> {
                    viewModel.isInfoVisible.value = !viewModel.isInfoVisible.value
                }
            }
            true
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ImageViewerAdapter(viewModel, requireActivity())
        binding.viewPager.setCurrentItem(viewModel.currentPosition, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putBoolean(KEY_INFO, isInfoVisible)
        }
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            viewModel.currentPosition = position
        }
    }


    companion object {
        private const val KEY_INFO = "KEY_INFO"
        fun newInstance(): ImageViewerFragment = ImageViewerFragment()
    }
}