package com.suraj.nasagallery.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.suraj.nasagallery.R
import com.suraj.nasagallery.data.ImageModel
import com.suraj.nasagallery.databinding.FragmentImageBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private var imageModel: ImageModel = ImageModel()
    val viewModel: ImagesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageModel = it.getParcelable(KEY_IMAGE_MODEL) ?: ImageModel()
        }
     }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater, container, false)
//        binding.toolbar.title = imageModel.title

        Glide.with(this)
            .load(imageModel.url)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    parentFragment?.startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    parentFragment?.startPostponedEnterTransition()
                    return false
                }

            })
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.imageView.setImageDrawable(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) { }
            })

        binding.titleText.text = imageModel.title
        binding.dateText.text = imageModel.date
        binding.descText.text = imageModel.explanation
        binding.copyrightText.text = imageModel.copyright
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isInfoVisible.collect {
                    binding.bottomLayout.visibility = if (it) View.VISIBLE else View.GONE
                }
            }
        }
    }

    companion object {
        private const val KEY_IMAGE_MODEL = "key_image_model"
        fun newInstance(imageModel: ImageModel): ImageFragment {
            return ImageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_IMAGE_MODEL, imageModel)
                }
            }
        }
    }

}