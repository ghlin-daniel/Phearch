package com.guanhaolin.pearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guanhaolin.pearch.api.model.VideoResponse
import com.guanhaolin.pearch.databinding.VideoViewHolderBinding

class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnVideoCellClickListener {
        fun onVideoCellClicked(video: VideoResponse)
    }

    private val videos: MutableList<VideoResponse> = ArrayList()

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

    fun update(newVideos: List<VideoResponse>) {
        videos.clear()
        videos.addAll(newVideos)
        notifyDataSetChanged()
    }

    fun clear() {
        videos.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding =
            VideoViewHolderBinding.inflate(LayoutInflater.from(parent.context))
        viewBinding.root.setOnClickListener(onClickListener)
        return VideoViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = videos[position]
        val viewHolder = holder as VideoViewHolder
        viewHolder.bind(video)
    }

    override fun getItemCount(): Int {
        return videos.size
    }
}
