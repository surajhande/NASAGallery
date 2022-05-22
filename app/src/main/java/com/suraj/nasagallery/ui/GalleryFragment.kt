package com.suraj.nasagallery.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.appbar.AppBarLayout
import com.suraj.nasagallery.R
import com.suraj.nasagallery.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private lateinit var adapter: GalleryAdapter
    private lateinit var binding: FragmentGalleryBinding
    private val viewModel: ImagesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        adapter = GalleryAdapter(this) {}
        binding.imageList.adapter = adapter

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.subTextLayout.alpha =
                1.0f - abs(verticalOffset / (appBarLayout?.totalScrollRange?.toFloat() ?: 1.0f))
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imagesUiModel.collect { onImagesUpdated(it) }
            }
        }
    }
    
    private fun onImagesUpdated(uiModel: ImagesUiModel) {
        when (uiModel) {
            ImagesUiModel.Loading -> {

            }
            ImagesUiModel.Error -> {

            }
            is ImagesUiModel.Success -> {
                adapter.submitList(uiModel.result)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = GalleryFragment()
    }
}