package com.dev.innso.myappbum.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.innso.myappbum.Adapters.RecycleAppbumAdapter;
import com.dev.innso.myappbum.Models.Appbum;
import com.dev.innso.myappbum.Models.FacadeModel;
import com.dev.innso.myappbum.Models.FactoryModel;
import com.dev.innso.myappbum.Providers.ServerConnection;
import com.dev.innso.myappbum.Utils.TAGs.JSONTag;
import com.dev.innso.myappbum.Utils.TAGs.SharedPrefKeys;
import com.dev.innso.myappbum.Utils.TAGs.ActivityTags;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.SharePreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by INNSO SAS on 22/05/2015.
 */
public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.main_recView)
    RecyclerView AlbumsList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();

    }

    private void init(){
        createList(FacadeModel.Appbums);
        String userName = SharePreferences.getApplicationValue(SharedPrefKeys.NAME_USER);
        if(userName == ""){
            Intent i = new Intent(this, StartActivity.class);
            startActivityForResult(i, ActivityTags.ACTIVITY_START.ordinal());
        }
        else{
            Pair<String,String> userId = new Pair<>(JSONTag.JSON_USER_ID.toString(),"1224525");
            new DownloadData().execute(userId);
        }
    }




    private void createList(ArrayList<Appbum> list){
        //Inicializacion RecyclerView
        AlbumsList.setHasFixedSize(true);
        final RecycleAppbumAdapter adaptador = new RecycleAppbumAdapter(list,this);

        AlbumsList.setAdapter(adaptador);
        AlbumsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchview = (SearchView)(menu.findItem(R.id.main_action_search).getActionView());
        searchview.setQueryHint(getResources().getString(R.string.find_Appbum));
        searchview.setOnQueryTextListener(new MainController());

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action_profile:
               Intent i = new Intent(this,ProfileActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ActivityTags.ACTIVITY_START.ordinal()){
            if(resultCode == RESULT_CANCELED){
                finish();
            }else{
                Pair<String,String> userId = new Pair<>(JSONTag.JSON_USER_ID.toString(),"1224525");
                new DownloadData().execute(userId);
            }

        }
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

                JSONObject jsonObject = new JSONObject(result);
                //publishProgress(result);

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
           // Toast.makeText(MainActivity.this,progress[0],Toast.LENGTH_LONG).show();
        }



        protected void onPostExecute(String result) {
            if(result != null) {
                createList(FacadeModel.Appbums);
            }
        }

    }

}
