package com.guanhaolin.pearch.ui.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.guanhaolin.pearch.api.model.VideoResponse
import com.guanhaolin.pearch.databinding.VideoViewHolderBinding

class VideoAdapter : ListAdapter<VideoResponse, VideoViewHolder>(DIFF_CALLBACK) {
    interface OnVideoCellClickListener {
        fun onVideoCellClicked(video: VideoResponse)
    }

    private var onVideoCellClickListener: OnVideoCellClickListener? = null

    private val onClickListener =
        View.OnClickListener { view ->
            val video = view.tag as VideoResponse
            onVideoCellClickListener?.onVideoCellClicked(video)
        }

    fun setOnVideoCellClickListener(
        onVideoCellClickListener: OnVideoCellClickListener?
    ) {
        this.onVideoCellClickListener = onVideoCellClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val viewBinding =
            VideoViewHolderBinding.inflate(LayoutInflater.from(parent.context))
        viewBinding.root.setOnClickListener(onClickListener)
        return VideoViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideoResponse>() {
    override fun areItemsTheSame(oldItem: VideoResponse, newItem: VideoResponse) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: VideoResponse, newItem: VideoResponse) =
        oldItem.views == newItem.views
                && oldItem.downloads == newItem.downloads
                && oldItem.likes == newItem.likes
                && oldItem.comments == newItem.comments
}
