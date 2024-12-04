package com.guanhaolin.pearch.ui.video

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.guanhaolin.pearch.api.model.VideoResponse
import com.guanhaolin.pearch.databinding.FragmentVideoBinding
import com.guanhaolin.pearch.ui.MediaViewModel
import com.guanhaolin.pearch.ui.util.SpaceItemDecoration
import com.guanhaolin.pearch.ui.video.VideoAdapter.OnVideoCellClickListener
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MediaViewModel by activityViewModel()

    private lateinit var layoutManager: StaggeredGridLayoutManager
    private lateinit var adapter: VideoAdapter

    private val onVideoCellClickListener =
        object : OnVideoCellClickListener {
            override fun onVideoCellClicked(video: VideoResponse) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.pageURL))
                startActivity(intent)
            }
        }

    private val onScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItemPositions = layoutManager.findFirstVisibleItemPositions(null)
                val firstVisibleItemPosition =
                    firstVisibleItemPositions[firstVisibleItemPositions.size - 1]

                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                    viewModel.loadMoreVideos();
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false).apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            videoListView.setLayoutManager(layoutManager)
            adapter = VideoAdapter()
            adapter.setOnVideoCellClickListener(onVideoCellClickListener)
            videoListView.setAdapter(adapter)
            videoListView.addItemDecoration(SpaceItemDecoration())
            videoListView.addOnScrollListener(onScrollListener)
        }

        viewModel
            .uiState
            .observe(
                viewLifecycleOwner
            ) { uiState ->
                val videos = uiState.videos
                showProgressBar(videos.isLoading && videos.data.isEmpty())
                adapter.update(videos.data)
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgressBar(show: Boolean) {
        binding.videoProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
