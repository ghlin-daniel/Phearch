package com.brickgit.imagesearch.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.model.MediaViewModel;

public class MainActivity extends AppCompatActivity {

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

	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setLogo(R.drawable.ic_logo);
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setDisplayShowTitleEnabled(false);

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
}
