package com.guanhaolin.pearch.adapter

import androidx.recyclerview.widget.RecyclerView
import com.guanhaolin.pearch.databinding.VideoViewHolderBinding
import com.guanhaolin.pearch.model.VideoInfoResponse

class VideoViewHolder(private val viewBinding: VideoViewHolderBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(videoInfo: VideoInfoResponse) {
        viewBinding.apply {
            root.tag = videoInfo

            preview.setImageURI(videoInfo.videos.small.thumbnail)
            likes.text = videoInfo.likes.count
            views.text = videoInfo.views.count
            downloads.text = videoInfo.downloads.count
            comments.text = videoInfo.comments.count
        }
    }
}
