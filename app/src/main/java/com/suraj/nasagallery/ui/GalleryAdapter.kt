package com.suraj.nasagallery.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.suraj.nasagallery.data.ImageModel
import com.suraj.nasagallery.databinding.ItemThumbnailBinding

class GalleryAdapter(
    private val fragment: GalleryFragment,
    val onThumbnailCilck: (View) -> Unit
) :
    ListAdapter<ImageModel, ThumbnailViewHolder>(ImageDiffCallback()) {

    lateinit var images: Array<Int>
    private val requestManager = Glide.with(fragment)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val binding = ItemThumbnailBinding.inflate(LayoutInflater.from(parent.context))
        return ThumbnailViewHolder(requestManager, binding)
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

class ThumbnailViewHolder(val requestManager: RequestManager, val binding: ItemThumbnailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ImageModel) {
        requestManager.load(model.url)
            .into(binding.thumbnail)
    }
}

