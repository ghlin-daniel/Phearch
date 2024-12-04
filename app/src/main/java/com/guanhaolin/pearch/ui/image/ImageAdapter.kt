package com.guanhaolin.pearch.ui.image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.databinding.ImageViewHolderBinding

class ImageAdapter : ListAdapter<ImageResponse, ImageViewHolder>(DIFF_CALLBACK) {
    interface OnImageCellClickListener {
        fun onImageCellClicked(image: ImageResponse)
    }

    private var onImageCellClickListener: OnImageCellClickListener? = null

    private val onClickListener =
        View.OnClickListener { view ->
            val image = view.tag as ImageResponse
            onImageCellClickListener?.onImageCellClicked(image)
        }

    fun setOnImageCellClickListener(onImageCellClickListener: OnImageCellClickListener?) {
        this.onImageCellClickListener = onImageCellClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val viewbinding =
            ImageViewHolderBinding.inflate(LayoutInflater.from(parent.context))
        viewbinding.root.setOnClickListener(onClickListener)
        return ImageViewHolder(viewbinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageResponse>() {
    override fun areItemsTheSame(oldItem: ImageResponse, newItem: ImageResponse) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ImageResponse, newItem: ImageResponse) =
        oldItem.views == newItem.views
                && oldItem.downloads == newItem.downloads
                && oldItem.likes == newItem.likes
                && oldItem.comments == newItem.comments
}
