package com.suraj.nasagallery.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
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

class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private var imageModel: ImageModel = ImageModel()

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
        return binding.root
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