package com.mario.watsontv.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.mario.watsontv.R;
import com.mario.watsontv.ui.auth.LoginActivity;
import com.mario.watsontv.ui.dashboard.dashboard.DashboardFragment;
import com.mario.watsontv.ui.dashboard.dashboard.DashboardListener;
import com.mario.watsontv.ui.dashboard.media.collections.list.CollectionListFragment;
import com.mario.watsontv.ui.dashboard.media.movies.list.MovieListFragment;
import com.mario.watsontv.ui.dashboard.media.series.list.SeriesListFragment;
import com.mario.watsontv.ui.dashboard.user.profile.ProfileFragment;
import com.mario.watsontv.util.UtilToken;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DashboardListener {

    FragmentTransaction fragmentChanger;
    TextView name, email;
    ImageView profilePic;

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
        View headerView = navigationView.getHeaderView(0);
        profilePic = headerView.findViewById(R.id.nav_header_profilePic);
        Glide.with(this).load(UtilToken.getProfilePic(this)).into(profilePic);
        name = headerView.findViewById(R.id.nav_header_name);
        email = headerView.findViewById(R.id.nav_header_email);
        name.setText(UtilToken.getName(this));
        email.setText(UtilToken.getEmail(this));
        DashboardFragment dashboardFragment = new DashboardFragment();
        fragmentChanger = getSupportFragmentManager().beginTransaction().replace(R.id.content_main_container, dashboardFragment);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        switch (id) {
            case R.id.nav_dashboard:
                DashboardFragment dashboardFragment = new DashboardFragment();
                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, dashboardFragment);
                fragmentChanger.commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_series:
                SeriesListFragment seriesFragment = new SeriesListFragment();
                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, seriesFragment);
                fragmentChanger.commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_movies:
                MovieListFragment moviesFragment = new MovieListFragment();
                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, moviesFragment);
                fragmentChanger.commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_collections:
                CollectionListFragment collectionListFragment = new CollectionListFragment();
                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, collectionListFragment);
                fragmentChanger.commit();
                drawer.closeDrawer(GravityCompat.START);

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
            case R.id.nav_profile:
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentChanger = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_main_container, profileFragment);
                fragmentChanger.commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_logout:
                logout();
                return true;
        }
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
