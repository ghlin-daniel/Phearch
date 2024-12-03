package com.guanhaolin.pearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.databinding.ImageViewHolderBinding

class ImageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnImageCellClickListener {
        fun onImageCellClicked(image: ImageResponse)
    }

    private val images: MutableList<ImageResponse> = ArrayList()

    private var onImageCellClickListener: OnImageCellClickListener? = null

    private val onClickListener =
        View.OnClickListener { view ->
            val image = view.tag as ImageResponse
            onImageCellClickListener?.onImageCellClicked(image)
        }

    fun setOnImageCellClickListener(onImageCellClickListener: OnImageCellClickListener?) {
        this.onImageCellClickListener = onImageCellClickListener
    }

    fun update(newPhotos: List<ImageResponse>) {
        images.clear()
        images.addAll(newPhotos)
        notifyDataSetChanged()
    }

    fun clear() {
        images.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewbinding =
            ImageViewHolderBinding.inflate(LayoutInflater.from(parent.context))
        viewbinding.root.setOnClickListener(onClickListener)
        return ImageViewHolder(viewbinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val image = images[position]
        val viewHolder = holder as ImageViewHolder
        viewHolder.bind(image)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
