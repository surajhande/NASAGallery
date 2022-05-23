package com.suraj.nasagallery.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.suraj.nasagallery.R
import com.suraj.nasagallery.databinding.FragmentImageViewerBinding
import timber.log.Timber

class ImageViewerFragment : Fragment() {

    private lateinit var binding: FragmentImageViewerBinding
    private val viewModel: ImagesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageViewerBinding.inflate(layoutInflater, container, false)

        binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback)
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
        fun newInstance(): ImageViewerFragment = ImageViewerFragment()
    }
}