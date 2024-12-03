package com.guanhaolin.pearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guanhaolin.pearch.databinding.VideoViewHolderBinding
import com.guanhaolin.pearch.model.VideoInfoResponse

class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnVideoCellClickListener {
        fun onVideoCellClicked(videoInfo: VideoInfoResponse?)
    }

    private val listVideoInfo: MutableList<VideoInfoResponse> = ArrayList()

    private var onVideoCellClickListener: OnVideoCellClickListener? = null

    private val onClickListener =
        View.OnClickListener { view ->
            if (onVideoCellClickListener != null) {
                val videoInfo = view.tag as VideoInfoResponse
                if (videoInfo != null) {
                    onVideoCellClickListener!!.onVideoCellClicked(videoInfo)
                }
            }
        }

    fun setOnVideoCellClickListener(
        onVideoCellClickListener: OnVideoCellClickListener?
    ) {
        this.onVideoCellClickListener = onVideoCellClickListener
    }

    fun update(newVideos: List<VideoInfoResponse>) {
        val size = listVideoInfo.size
        listVideoInfo.clear()
        listVideoInfo.addAll(newVideos)
        notifyItemRangeInserted(size, newVideos.size - size)
    }

    fun clear() {
        listVideoInfo.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding =
            VideoViewHolderBinding.inflate(LayoutInflater.from(parent.context))
        viewBinding.root.setOnClickListener(onClickListener)
        return VideoViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val videoInfo = listVideoInfo[position]
        val viewHolder = holder as VideoViewHolder
        viewHolder.bind(videoInfo)
    }

    override fun getItemCount(): Int {
        return listVideoInfo.size
    }
}
