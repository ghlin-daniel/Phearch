package com.guanhaolin.pearch.adapter

import androidx.recyclerview.widget.RecyclerView
import com.guanhaolin.pearch.databinding.ImageViewHolderBinding
import com.guanhaolin.pearch.model.ImageInfoResponse

class ImageViewHolder(private val viewBinding: ImageViewHolderBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(imageInfo: ImageInfoResponse) {
        viewBinding.apply {
            root.tag = imageInfo
            image.setImageURI(imageInfo.webformatURL)
            likes.text = imageInfo.likes.count
            views.text = imageInfo.views.count
            downloads.text = imageInfo.downloads.count
            comments.text = imageInfo.comments.count
        }
    }
}
