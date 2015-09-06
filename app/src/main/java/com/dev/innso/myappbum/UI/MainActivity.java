package com.dev.innso.myappbum.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.dev.innso.myappbum.Adapters.RecycleAppbumAdapter;
import com.dev.innso.myappbum.Animation.GuillotineAnimation;
import com.dev.innso.myappbum.Models.Appbum;
import com.dev.innso.myappbum.Models.FacadeModel;
import com.dev.innso.myappbum.Models.FactoryModel;
import com.dev.innso.myappbum.Providers.ServerConnection;
import com.dev.innso.myappbum.UI.views.MainMenu;
import com.dev.innso.myappbum.Utils.TAGs.JSONTag;
import com.dev.innso.myappbum.Utils.TAGs.SharedPrefKeys;
import com.dev.innso.myappbum.Utils.TAGs.ActivityTags;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.SharePreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by INNSO SAS on 22/05/2015.
 */
public class MainActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;

    @Bind(R.id.main_recView)
    RecyclerView AlbumsList;

    @Bind(R.id.root)
    FrameLayout root;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.content_hamburger)
    View contentHamburger;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();


        setSupportActionBar(toolbar);
        MainMenu menu = new MainMenu(this);
        root.addView(menu.getRootView());

        new GuillotineAnimation.GuillotineBuilder(menu, menu.findViewById(R.id.menu_imgmenu), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setClosedOnStart(true)
                .setActionBarViewForAnimation(toolbar)
                .build();
    }

    private void init(){
        createList(FacadeModel.Appbums);
        String userID = SharePreferences.getApplicationValue(SharedPrefKeys.ID_USER);
        if(userID == ""){
            Intent i = new Intent(this, StartActivity.class);
            startActivityForResult(i, ActivityTags.ACTIVITY_START.ordinal());
        }
        else{
            Pair<String,String> userId = new Pair<>(JSONTag.JSON_USER_ID.toString(),userID);
            new DownloadData().execute(userId);
        }
    }


    private void createList(ArrayList<Appbum> list){
        AlbumsList.setHasFixedSize(true);
        final RecycleAppbumAdapter adaptador = new RecycleAppbumAdapter(list,this);

        AlbumsList.setAdapter(adaptador);
        AlbumsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchview = (SearchView)(menu.findItem(R.id.main_action_search).getActionView());
        searchview.setQueryHint(getResources().getString(R.string.find_Appbum));
        searchview.setOnQueryTextListener(new MainController());

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ActivityTags.ACTIVITY_START.ordinal()){
            if(resultCode == RESULT_CANCELED){
                finish();
            }else{
                String userId = SharePreferences.getApplicationValue(SharedPrefKeys.ID_USER);
                Pair<String,String> pairId = new Pair<>(JSONTag.JSON_USER_ID.toString(),userId);
                new DownloadData().execute(pairId);
            }
        }
    }


    @OnClick(R.id.main_add) void closeSession(){
        SharePreferences.resetUser();
    }


    public class MainController implements SearchView.OnQueryTextListener{

        @Override
        public boolean onQueryTextSubmit(String query) {
            ((RecycleAppbumAdapter) AlbumsList.getAdapter()).setFilter(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            ((RecycleAppbumAdapter) AlbumsList.getAdapter()).setFilter(newText);
            return false;
        }
    }


    private class DownloadData extends AsyncTask< Pair<String,String>,String,String>{

        protected String doInBackground(Pair<String,String>...data){
            try{

                String result = ServerConnection.requestPOST(getResources().getString(R.string.getAppbumsService), data);
                //publishProgress(result);
                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("Appbums");
                FactoryModel.createAppbums(jsonArray);

                return "Success";

            }
            catch (Exception e) {
                Log.d("ReadWeatherJSONFeedTask", e.getMessage());
            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {
            Toast.makeText(MainActivity.this, progress[0], Toast.LENGTH_LONG).show();
        }


        protected void onPostExecute(String result) {
            if(result != null) {
                createList(FacadeModel.Appbums);
            }
        }

    }

}
