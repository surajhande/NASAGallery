package com.suraj.nasagallery.ui

import android.graphics.drawable.Drawable
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.suraj.nasagallery.R
import com.suraj.nasagallery.data.ImageModel
import com.suraj.nasagallery.databinding.ItemThumbnailBinding
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class GalleryAdapter(
    private val viewModel: ImagesViewModel,
    private val fragment: GalleryFragment
) :
    ListAdapter<ImageModel, ThumbnailViewHolder>(ImageDiffCallback()) {

    private val requestManager = Glide.with(fragment)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val binding = ItemThumbnailBinding.inflate(LayoutInflater.from(parent.context))
        return ThumbnailViewHolder(
            ViewHolderListenerImpl(viewModel, fragment),
            requestManager,
            binding
        )
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class ImageDiffCallback : DiffUtil.ItemCallback<ImageModel>() {
    override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return (oldItem.url == newItem.url &&
                oldItem.copyright == newItem.copyright &&
                oldItem.title == newItem.title &&
                oldItem.mediaType == newItem.mediaType &&
                oldItem.hdUrl == newItem.hdUrl)
    }

}

interface ViewHolderListener {
    fun onLoadCompleted(view: ImageView?, adapterPosition: Int)
    fun onItemClicked(view: View?, adapterPosition: Int)
}

class ViewHolderListenerImpl(
    private val viewModel: ImagesViewModel,
    private val fragment: GalleryFragment) : ViewHolderListener {

    override fun onLoadCompleted(view: ImageView?, adapterPosition: Int) {

    }

    override fun onItemClicked(view: View?, adapterPosition: Int) {

        viewModel.currentPosition = adapterPosition

        fragment.requireActivity().supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.fragmentContainerView, ImageViewerFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

}

class ThumbnailViewHolder(
    private val viewHolderListener: ViewHolderListener,
    private val requestManager: RequestManager,
    private val binding: ItemThumbnailBinding
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.thumbnailItem.setOnClickListener(this)
    }

    fun bind(model: ImageModel) {

        requestManager.load(model.url)
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewHolderListener.onLoadCompleted(binding.thumbnail, adapterPosition)
                    return false
                }
            })
            .into(binding.thumbnail)
    }

    override fun onClick(view: View?) {
        viewHolderListener.onItemClicked(view, adapterPosition)
    }
}

