package com.dev.innso.myappbum.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.adapters.RecycleAppbumAdapter;
import com.dev.innso.myappbum.api.controllers.AppbumController;
import com.dev.innso.myappbum.di.ApiModule;
import com.dev.innso.myappbum.di.component.AppComponent;
import com.dev.innso.myappbum.di.component.DaggerAppComponent;
import com.dev.innso.myappbum.managers.AppPreference;
import com.dev.innso.myappbum.managers.preferences.ManagerPreferences;
import com.dev.innso.myappbum.models.Appbum;
import com.dev.innso.myappbum.utils.tags.ActivityTags;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private Toolbar toolbar;

    private LinearLayout emptyTextMessage;

    private NavigationView navigationView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private FloatingActionButton floatingActionButton;

    private RecyclerView albumsList;

    private DrawerLayout drawerLayout;

    private TextView profileName;

    private ImageView profileCover;

    private ImageView profilePicture;

    private RecycleAppbumAdapter listAdapter;

    private ArrayList dataList;

    @Inject
    AppbumController controller;

    @Inject
    ManagerPreferences managerPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        AppComponent daggerComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();

        daggerComponent.inject(this);

        initViews();

        configureViews();

        initProfile();

        initListeners();

        getUserAppbums();
    }

    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        albumsList = (RecyclerView) findViewById(R.id.list_mainAppbums);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingButton_main);

        navigationView = (NavigationView) findViewById(R.id.main_navigationView);

        emptyTextMessage = (LinearLayout) findViewById(R.id.layout_emptyList_appbum);

        View headerView = navigationView.getHeaderView(0);

        profileName = (TextView) headerView.findViewById(R.id.profile_name);

        profileCover = (ImageView) headerView.findViewById(R.id.profile_cover);

        profilePicture = (ImageView) headerView.findViewById(R.id.imageView_profile);
    }

    private void configureViews() {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        dataList = new ArrayList();

        albumsList.setHasFixedSize(true);

        listAdapter = new RecycleAppbumAdapter(dataList, this);

        albumsList.setAdapter(listAdapter);

        albumsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void getUserAppbums() {

        controller.getUserAppbums(managerPreferences.getString(AppPreference.USER_ID), new AppbumController.AppbumResponse() {
            @Override
            public void success(ArrayList<Appbum> appbumList) {

                albumsList.setVisibility(View.VISIBLE);

                dataList = appbumList;

                listAdapter.setData(dataList);

                swipeRefreshLayout.setRefreshing(false);

                if (dataList.size() != 0) {

                    listAdapter.notifyDataSetChanged();

                    emptyTextMessage.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFail(String errorMessage) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initProfile() {
        String userId = managerPreferences.getString(AppPreference.FACEBOOK_USERID);
        String userName = managerPreferences.getString(AppPreference.NAME_USER);
        String userCover = managerPreferences.getString(AppPreference.COVER_USER);

        if (!TextUtils.isEmpty(userId)) {
            String imageURL = getString(R.string.url_facebook_image_profile, userId);
            Picasso.with(this).load(imageURL).placeholder(R.drawable.ic_profile).into(profilePicture);
            Picasso.with(this).load(userCover).placeholder(R.mipmap.default_bg).into(profileCover);

        }

        profileName.setText(userName.toUpperCase());
    }

    private void initListeners() {

        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout.setOnRefreshListener(this);

        floatingActionButton.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_menu_close_session) {
            managerPreferences.resetPreferences();
            startActivity(SplashActivity.class);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startActivity(Class activity) {
        startActivity(new Intent(this, activity));
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityTags.ACTIVITY_START.ordinal()) {
            if (resultCode == RESULT_CANCELED) {
                finish();
            } else {
                getUserAppbums();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchview = (SearchView) (menu.findItem(R.id.main_action_search).getActionView());

        searchview.setQueryHint(getResources().getString(R.string.find_Appbum));

        searchview.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        getUserAppbums();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        ((RecycleAppbumAdapter) albumsList.getAdapter()).setFilter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((RecycleAppbumAdapter) albumsList.getAdapter()).setFilter(newText);
        return false;
    }

}
