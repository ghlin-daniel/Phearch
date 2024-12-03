package com.guanhaolin.pearch.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.guanhaolin.pearch.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    private lateinit var tabLayout: TabLayout

    private lateinit var viewPager: ViewPager2

    private lateinit var searchView: SearchView
    private val mViewModel: MediaViewModel by viewModel()

    private val onQueryTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val formattedQuery = query.trim { it <= ' ' }
                if (formattedQuery.isEmpty()) {
                    searchView.setQuery("", false)
                    return true
                }

                mViewModel.queryPhotos(formattedQuery)

                //          mViewModel.queryVideos(query);
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
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.view_pager)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.ic_logo)
            actionBar.setDisplayUseLogoEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }

        initViewPager(viewPager)
        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            if (position == 0) {
                tab.setText("Photo")
            } else {
                tab.setText("Video")
            }
        }
            .attach()
        initTabIcons(tabLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(onQueryTextListener)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initTabIcons(tabLayout: TabLayout) {
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_tab_photo)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_tab_video)
    }

    private fun initViewPager(viewPager: ViewPager2) {
        val adapter = MainPagerAdapter(this)
        viewPager.adapter = adapter
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
}
