package com.guanhaolin.pearch.ui.image

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.databinding.FragmentImageBinding
import com.guanhaolin.pearch.ui.MediaViewModel
import com.guanhaolin.pearch.ui.image.ImageAdapter.OnImageCellClickListener
import com.guanhaolin.pearch.ui.util.SpaceItemDecoration
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ImageFragment : Fragment() {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MediaViewModel by activityViewModel()

    private lateinit var layoutManager: StaggeredGridLayoutManager
    private lateinit var adapter: ImageAdapter

    private val onImageCellClickListener =
        object : OnImageCellClickListener {
            override fun onImageCellClicked(image: ImageResponse) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(image.pageURL))
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
                    viewModel.loadMorePhotos()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false).apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            imageListView.setLayoutManager(layoutManager)
            adapter = ImageAdapter()
            adapter.setOnImageCellClickListener(onImageCellClickListener)
            imageListView.setAdapter(adapter)
            imageListView.addItemDecoration(SpaceItemDecoration())
            imageListView.addOnScrollListener(onScrollListener)
        }

        viewModel
            .uiState
            .observe(
                viewLifecycleOwner
            ) { uiState ->
                val images = uiState.images
                showProgressBar(images.isLoading && images.data.isEmpty())
                adapter.submitList(images.data)
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgressBar(show: Boolean) {
        binding.imageProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
