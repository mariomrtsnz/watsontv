package com.mario.watsontv.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.mario.watsontv.R;
import com.mario.watsontv.ui.auth.LoginActivity;
import com.mario.watsontv.ui.dashboard.dashboard.DashboardFragment;
import com.mario.watsontv.ui.dashboard.dashboard.DashboardListener;
import com.mario.watsontv.util.UtilToken;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DashboardListener {

    FragmentTransaction fragmentChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DashboardFragment dashboardFragment = new DashboardFragment();
        fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, dashboardFragment);
        fragmentChanger.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_dashboard:
                DashboardFragment dashboardFragment = new DashboardFragment();
                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, dashboardFragment);
                fragmentChanger.commit();
                return true;
            case R.id.nav_series:
//                SeriesFragment seriesFragment = new SeriesFragment();
//                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, seriesFragment);
                fragmentChanger.commit();
                return true;
            case R.id.nav_movies:
//                MoviesFragment moviesFragment = new MoviesFragment();
//                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, moviesFragment);
                fragmentChanger.commit();
                return true;
            case R.id.nav_collections:
//                CollectionsFragment collectionsFragment = new CollectionsFragment();
//                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, collectionsFragment);
                fragmentChanger.commit();
                return true;
            case R.id.nav_watchlist:
//                WatchlistFragment watchlistFragment = new WatchlistFragment();
//                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, watchlistFragment);
                fragmentChanger.commit();
                return true;
            case R.id.nav_favorites:
//                FavoritestFragment favoritesFragment = new FavoritestFragment();
//                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, favoritesFragment);
                fragmentChanger.commit();
                return true;
            case R.id.nav_logout:
                logout();
                return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        UtilToken.setId(this, null);
        UtilToken.setToken(this, null);
        UtilToken.setUserLoggedData(this, null);
        finish();
        Intent logoutIntent = new Intent(this, LoginActivity.class);
        logoutIntent.putExtra("isLogin", true);
        startActivity(logoutIntent);
    }
}
