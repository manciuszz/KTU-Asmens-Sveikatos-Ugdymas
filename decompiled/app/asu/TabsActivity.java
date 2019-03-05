package app.asu;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import java.util.List;

public abstract class TabsActivity extends NavigationActivity {
    private ActionBar actionBar;
    private TabListener tabListener;

    private class CustomPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> fragments;
        private final String[] titles;

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = TabsActivity.this.setupTabs();
            if (this.fragments.size() == 3) {
                this.titles = TabsActivity.this.getResources().getStringArray(R.array.foodMenu);
            } else {
                this.titles = TabsActivity.this.getResources().getStringArray(R.array.sportsMenu);
            }
            if (TabsActivity.this.actionBar != null) {
                for (int i = 0; i < this.fragments.size(); i++) {
                    TabsActivity.this.actionBar.addTab(TabsActivity.this.actionBar.newTab().setText(this.titles[i]).setTabListener(TabsActivity.this.tabListener));
                }
            }
        }

        public Fragment getItem(int position) {
            return (Fragment) this.fragments.get(position);
        }

        public int getCount() {
            return this.fragments.size();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        this.actionBar = getActionBar();
        if (this.actionBar != null) {
            this.actionBar.setNavigationMode(2);
        }
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        this.tabListener = new TabListener() {
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            }

            public void onTabReselected(Tab tab, FragmentTransaction ft) {
            }
        };
        mViewPager.setAdapter(new CustomPagerAdapter(getFragmentManager()));
        mViewPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                if (TabsActivity.this.actionBar != null) {
                    TabsActivity.this.actionBar.setSelectedNavigationItem(position);
                }
            }
        });
    }

    List<Fragment> setupTabs() {
        return null;
    }
}
