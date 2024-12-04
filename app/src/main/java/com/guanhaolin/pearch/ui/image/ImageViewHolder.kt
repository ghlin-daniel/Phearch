package com.guanhaolin.pearch.ui.image

import androidx.recyclerview.widget.RecyclerView
import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.databinding.ImageViewHolderBinding

class ImageViewHolder(private val viewBinding: ImageViewHolderBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(image: ImageResponse) {
        viewBinding.apply {
            root.tag = image
            imageView.setImageURI(image.webformatURL)
            likes.text = "${image.likes}"
            views.text = "${image.views}"
            downloads.text = "${image.downloads}"
            comments.text = "${image.comments}"
        }
    }
}
