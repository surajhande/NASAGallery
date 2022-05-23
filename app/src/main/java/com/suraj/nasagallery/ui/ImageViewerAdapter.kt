package com.suraj.nasagallery.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImageViewerAdapter(private val viewModel: ImagesViewModel, fa: FragmentActivity) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = viewModel.getImages().size

    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(viewModel.getImages()[position])
    }
}