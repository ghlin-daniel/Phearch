package com.guanhaolin.pearch.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.guanhaolin.pearch.R;

public class MainActivity extends AppCompatActivity {

  private Toolbar toolbar;

  private TabLayout tabLayout;

  private ViewPager2 viewPager;

  private SearchView searchView;
  private MediaViewModel mViewModel;

  private final SearchView.OnQueryTextListener onQueryTextListener =
      new SearchView.OnQueryTextListener() {
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

    toolbar = findViewById(R.id.toolbar);
    tabLayout = findViewById(R.id.tabs);
    viewPager = findViewById(R.id.view_pager);

    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setLogo(R.drawable.ic_logo);
      actionBar.setDisplayUseLogoEnabled(true);
      actionBar.setDisplayShowHomeEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
    }

    initViewPager(viewPager);
    new TabLayoutMediator(
            tabLayout,
            viewPager,
            (tab, position) -> {
              if (position == 0) {
                tab.setText("Photo");
              } else {
                tab.setText("Video");
              }
            })
        .attach();
    initTabIcons(tabLayout);

    mViewModel = new ViewModelProvider(this).get(MediaViewModel.class);
    mViewModel.getPhotos();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    searchView = (SearchView) menu.findItem(R.id.search).getActionView();
    searchView.setOnQueryTextListener(onQueryTextListener);
    return super.onCreateOptionsMenu(menu);
  }

  private void initTabIcons(TabLayout tabLayout) {
    tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_photo);
    tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_video);
  }

  private void initViewPager(ViewPager2 viewPager) {
    MainPagerAdapter adapter = new MainPagerAdapter(this);
    viewPager.setAdapter(adapter);
  }

  private static class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
      super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      if (position == 0) {
        return new ImageFragment();
      } else {
        return new VideoFragment();
      }
    }

    @Override
    public int getItemCount() {
      return 2;
    }
  }
}
