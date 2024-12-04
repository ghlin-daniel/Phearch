package com.guanhaolin.pearch.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.guanhaolin.pearch.R
import com.guanhaolin.pearch.databinding.ActivityMainBinding
import com.guanhaolin.pearch.ui.image.ImageFragment
import com.guanhaolin.pearch.ui.video.VideoFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAB_TITLE_PHOTO = "Photo"
        private const val TAB_TITLE_VIDEO = "Video"
    }

    private lateinit var searchView: SearchView
    private lateinit var viewBinding: ActivityMainBinding

    private val viewModel: MediaViewModel by viewModel()

    private val onQueryTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val formattedQuery = query.trim { it <= ' ' }
                if (formattedQuery.isEmpty()) {
                    searchView.setQuery("", false)
                    return true
                }

                viewModel.query(formattedQuery)
                searchView.setQuery("", false)
                searchView.isIconified = true
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            setSupportActionBar(toolbar)
            supportActionBar?.let {
                it.setLogo(R.drawable.ic_logo)
                it.setDisplayUseLogoEnabled(true)
                it.setDisplayShowHomeEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }

            TabLayoutMediator(tabs, viewPager) { tab: TabLayout.Tab, position: Int ->
                if (position == 0) {
                    tab.setText(TAB_TITLE_PHOTO)
                } else {
                    tab.setText(TAB_TITLE_VIDEO)
                }
            }.attach()
            tabs.getTabAt(0)!!.setIcon(R.drawable.ic_tab_photo)
            tabs.getTabAt(1)!!.setIcon(R.drawable.ic_tab_video)

            viewPager.adapter = MainPagerAdapter(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(onQueryTextListener)
        return super.onCreateOptionsMenu(menu)
    }
}

private class MainPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            ImageFragment()
        } else {
            VideoFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
