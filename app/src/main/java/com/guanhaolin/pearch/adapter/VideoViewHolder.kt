package com.guanhaolin.pearch.adapter

import androidx.recyclerview.widget.RecyclerView
import com.guanhaolin.pearch.api.model.VideoResponse
import com.guanhaolin.pearch.databinding.VideoViewHolderBinding

class VideoViewHolder(private val viewBinding: VideoViewHolderBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(video: VideoResponse) {
        viewBinding.apply {
            root.tag = video
            preview.setImageURI(video.videos.small.thumbnail)
            likes.text = "${video.likes}"
            views.text = "${video.views}"
            downloads.text = "${video.downloads}"
            comments.text = "${video.comments}"
        }
    }
}
