package com.brickgit.imagesearch.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.model.MediaViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.tabs) TabLayout tabLayout;
	@BindView(R.id.view_pager) ViewPager viewPager;

	private SearchView searchView;
	private MediaViewModel mViewModel;

    private final SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            query = query.trim();
            if (query.isEmpty()) {
                searchView.setQuery("", false);
                return true;
            }

	        mViewModel.queryPhotos(query);
            mViewModel.queryVideos(query);

            searchView.setQuery("", false);
            searchView.setIconified(true);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	    ButterKnife.bind(this);

	    setSupportActionBar(toolbar);
	    ActionBar actionBar = getSupportActionBar();
	    if (actionBar != null) {
	    	actionBar.setLogo(R.drawable.ic_logo);
		    actionBar.setDisplayUseLogoEnabled(true);
		    actionBar.setDisplayShowHomeEnabled(true);
		    actionBar.setDisplayShowTitleEnabled(false);
	    }

	    initViewPager(viewPager);
	    tabLayout.setupWithViewPager(viewPager);
	    initTabIcons(tabLayout);

	    mViewModel = ViewModelProviders.of(this).get(MediaViewModel.class);
	    mViewModel.getPhotos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    private void initTabIcons(TabLayout tabLayout) {
    	tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_photo);
    	tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_video);
    }

    private void initViewPager(ViewPager viewPager) {
	    MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
	    adapter.addFragment(new ImageFragment());
	    adapter.addFragment(new VideoFragment());
	    viewPager.setAdapter(adapter);
	}

	private class MainPagerAdapter extends FragmentPagerAdapter {

		private final List<Fragment> fragments = new ArrayList<>();

		public MainPagerAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		public void addFragment(Fragment fragment) {
			fragments.add(fragment);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}
	}
}
