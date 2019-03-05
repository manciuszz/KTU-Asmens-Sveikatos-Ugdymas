package app.asu;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class NavigationActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private String[] mTitles;

    private class DrawerItemClickListener implements OnItemClickListener {
        private DrawerItemClickListener() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (PreferenceManager.getDefaultSharedPreferences(NavigationActivity.this.getApplicationContext()).getString(SettingsActivity.NAME, "").length() == 0 && position == 0) {
                Toast.makeText(NavigationActivity.this.getApplicationContext(), "Norėdami peržiūrėti tvarkaraštį, įveskite KTU prisijungimo vardą", 0).show();
            } else {
                NavigationActivity.this.selectItem(position);
            }
        }
    }

    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(3)) {
            startActivity(new Intent(this, StartActivity.class));
        } else {
            this.mDrawerLayout.openDrawer(3);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation, null);
        setContentView(this.mDrawerLayout);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }
        this.mTitles = getResources().getStringArray(R.array.menu);
        this.mDrawerList = (ListView) findViewById(R.id.left_drawer);
        this.mDrawerList.setAdapter(new ArrayAdapter(this, 17367043, this.mTitles));
        this.mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawerLayout, R.drawable.ic_navigation_drawer, 2131034138, 2131034138) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                NavigationActivity.this.invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                NavigationActivity.this.invalidateOptionsMenu();
            }

            public void onDrawerStateChanged(int newState) {
                if (newState == 2) {
                    if (!NavigationActivity.this.mDrawerLayout.isDrawerOpen(3)) {
                        ((TextView) NavigationActivity.this.mDrawerList.getChildAt(0).findViewById(16908308)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tvarkarastis_navigacija, 0, 0, 0);
                        ((TextView) NavigationActivity.this.mDrawerList.getChildAt(1).findViewById(16908308)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mityba_navigacija, 0, 0, 0);
                        ((TextView) NavigationActivity.this.mDrawerList.getChildAt(2).findViewById(16908308)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sportas_navigacija, 0, 0, 0);
                        ((TextView) NavigationActivity.this.mDrawerList.getChildAt(3).findViewById(16908308)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fizinis_aktyvumas_navigacija, 0, 0, 0);
                        ((TextView) NavigationActivity.this.mDrawerList.getChildAt(4).findViewById(16908308)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_informacija_navigacija, 0, 0, 0);
                        ((TextView) NavigationActivity.this.mDrawerList.getChildAt(5).findViewById(16908308)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_nustatymai_navigacija, 0, 0, 0);
                    }
                    NavigationActivity.this.invalidateOptionsMenu();
                }
            }
        };
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
    }

    public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, (FrameLayout) this.mDrawerLayout.findViewById(R.id.content_frame), true);
        super.setContentView(this.mDrawerLayout);
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
        if (getActionBar() != null) {
            getActionBar().setTitle(this.mTitle);
        }
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {
        Intent intent;
        this.mDrawerList.setItemChecked(position, true);
        setTitle(this.mTitles[position]);
        switch (position) {
            case 0:
                intent = new Intent(this, ScheduleActivity.class);
                break;
            case 1:
                intent = new Intent(this, NutritionActivity.class);
                break;
            case 2:
                intent = new Intent(this, SportsActivity.class);
                break;
            case 3:
                intent = new Intent(this, ErgonomicsActivity.class);
                break;
            case 4:
                intent = new Intent(this, InformationActivity.class);
                break;
            case 5:
                intent = new Intent(this, SettingsActivity.class);
                break;
            default:
                intent = null;
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
