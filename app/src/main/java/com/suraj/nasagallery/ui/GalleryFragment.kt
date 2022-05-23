package com.suraj.nasagallery.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
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
    private val viewModel: ImagesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        adapter = GalleryAdapter(viewModel,this)
        binding.imageList.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

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

    override fun onStop() {
        super.onStop()
    }

    companion object {

        @JvmStatic
        fun newInstance() = GalleryFragment()
    }
}