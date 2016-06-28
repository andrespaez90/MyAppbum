package com.dev.innso.myappbum.ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BinderThread;
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
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.utils.SharePreferences;
import com.dev.innso.myappbum.utils.tags.ActivityTags;
import com.dev.innso.myappbum.utils.tags.JSONTag;
import com.dev.innso.myappbum.utils.tags.SharedPrefKeys;
import com.dev.innso.myappbum.adapters.RecycleAppbumAdapter;
import com.dev.innso.myappbum.models.FacadeModel;
import com.dev.innso.myappbum.models.FactoryModel;
import com.dev.innso.myappbum.providers.ServerConnection;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.main_recView)
    RecyclerView albumsList;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.imageView_profile)
    ImageView profilePicture;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.profile_cover)
    ImageView profileCover;

    @Bind(R.id.profile_name)
    TextView profileName;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private RecycleAppbumAdapter listAdapter;

    private ArrayList dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initProfile();
        initListeners();
        init();
    }

    private void init() {
        String userId = SharePreferences.getApplicationValue(SharedPrefKeys.USER_ID);

        Pair<String, String> userData = new Pair<>(JSONTag.JSON_USER_ID.toString(), userId);

        new DownloadData().execute(userData);
    }

    private void initViews() {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(toggle);

        toggle.syncState();

        dataList = FacadeModel.Appbums;

        albumsList.setHasFixedSize(true);

        listAdapter = new RecycleAppbumAdapter(dataList, this);

        albumsList.setAdapter(listAdapter);

        albumsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initProfile() {
        String userId = SharePreferences.getApplicationValue(SharedPrefKeys.FACEBOOK_USERID);
        String userName = SharePreferences.getApplicationValue(SharedPrefKeys.NAME_USER);
        String userCover = SharePreferences.getApplicationValue(SharedPrefKeys.COVER_USER);

        if (!TextUtils.isEmpty(userId)) {
            String imageURL = "https://graph.facebook.com/" + userId + "/picture?type=large";
            Picasso.with(this).load(imageURL).placeholder(R.drawable.ic_profile).into(profilePicture);
            Picasso.with(this).load(userCover).placeholder(R.mipmap.default_bg).into(profileCover);

        } else {

        }
        profileName.setText(userName.toUpperCase());
    }

    private void initListeners() {

        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout.setOnRefreshListener(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

        } else if (id == R.id.nav_send) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityTags.ACTIVITY_START.ordinal()) {
            if (resultCode == RESULT_CANCELED) {
                finish();
            } else {
                String userId = SharePreferences.getApplicationValue(SharedPrefKeys.USER_ID);
                Pair<String, String> pairId = new Pair<>(JSONTag.JSON_USER_ID.toString(), userId);
                new DownloadData().execute(pairId);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchview = (SearchView) (menu.findItem(R.id.main_action_search).getActionView());

        searchview.setQueryHint(getResources().getString(R.string.find_Appbum));

        searchview.setOnQueryTextListener(new MainController());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        init();
    }

    private class DownloadData extends AsyncTask<Pair<String, String>, String, String> {

        protected String doInBackground(Pair<String, String>... data) {
            try {

                String result = ServerConnection.requestPOST(getResources().getString(R.string.getAppbumsService), data);
                publishProgress(result);
                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("Appbums");
                FactoryModel.createAppbums(jsonArray);

                return "Success";

            } catch (Exception e) {
                Log.d("ReadWeatherJSONFeedTask", e.getMessage());
            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {
            Log.v("JSON", progress[0]);
        }


        protected void onPostExecute(String result) {
            listAdapter.notifyDataSetChanged();
            ((RecycleAppbumAdapter) albumsList.getAdapter()).setFilter("");
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public class MainController implements SearchView.OnQueryTextListener {

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
}
