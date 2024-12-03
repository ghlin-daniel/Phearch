package com.guanhaolin.pearch.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.guanhaolin.pearch.R
import com.guanhaolin.pearch.adapter.SpaceItemDecoration
import com.guanhaolin.pearch.adapter.VideoAdapter
import com.guanhaolin.pearch.adapter.VideoAdapter.OnVideoCellClickListener
import com.guanhaolin.pearch.model.VideoInfoResponse

class VideoFragment : Fragment() {
    private var mViewModel: MediaViewModel? = null

    var progressBar: ProgressBar? = null

    var listView: RecyclerView? = null

    private var layoutManager: StaggeredGridLayoutManager? = null

    private val onVideoCellClickListener =
        OnVideoCellClickListener { videoInfo: VideoInfoResponse ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoInfo.pageURL))
            startActivity(intent)
        }

    private val onScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager!!.itemCount
                val visibleItemCount = layoutManager!!.childCount
                val firstVisibleItemPositions = layoutManager!!.findFirstVisibleItemPositions(null)
                val firstVisibleItemPosition =
                    firstVisibleItemPositions[firstVisibleItemPositions.size - 1]

                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
//            mViewModel.loadMoreVideos();
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        listView = view.findViewById(R.id.video_list_view)
        progressBar = view.findViewById(R.id.video_progress_bar)

        layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        listView.setLayoutManager(layoutManager)
        val adapter = VideoAdapter()
        adapter.setOnVideoCellClickListener(onVideoCellClickListener)
        listView.setAdapter(adapter)
        listView.addItemDecoration(SpaceItemDecoration())
        listView.addOnScrollListener(onScrollListener)

        mViewModel = ViewModelProviders.of(activity!!).get(
            MediaViewModel::class.java
        )
        mViewModel!!
            .query
            .observe(
                viewLifecycleOwner
            ) { query: String? ->
                showProgressBar(true)
                adapter.clear()
            }
        mViewModel!!
            .videos
            .observe(
                viewLifecycleOwner
            ) { imageInfoResponses: List<VideoInfoResponse?>? ->
                showProgressBar(false)
                adapter.update(imageInfoResponses)
            }

        return view
    }

    private fun showProgressBar(show: Boolean) {
        progressBar!!.visibility =
            if (show) View.VISIBLE else View.INVISIBLE
    }
}
