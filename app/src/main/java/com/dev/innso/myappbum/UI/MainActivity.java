package com.dev.innso.myappbum.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.innso.myappbum.Adapters.RecycleAppbumAdapter;
import com.dev.innso.myappbum.Models.ItemImageList;
import com.dev.innso.myappbum.Models.SharedPrefKeys;
import com.dev.innso.myappbum.Utils.ActivityTags;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.SharePreferences;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by INNSO SAS on 22/05/2015.
 */
public class MainActivity extends ActionBarActivity {

    private ArrayList<ItemImageList> itemsImagelist;

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
        String userName = SharePreferences.getApplicationValue(SharedPrefKeys.NAME_USER);
        if(userName == ""){
            Intent i = new Intent(this, StartActivity.class);
            startActivityForResult(i, ActivityTags.ACTIVITY_START.ordinal());
        }
        else{
            getAppbums();
            createList(itemsImagelist);
        }
    }

    public void getAppbums() {
        itemsImagelist = new ArrayList<ItemImageList>();
        itemsImagelist.add(new ItemImageList("Basilea - Dia de las Madres 2014" , "http://andrespaez90.com/images/Basilea/P14.JPG" ) );
        itemsImagelist.add(new ItemImageList("Basilea - Halloween" , "http://andrespaez90.com/images/Basilea/P14.JPG" ) );
        itemsImagelist.add(new ItemImageList("Basilea - Dia de las Madres 2014" , "http://andrespaez90.com/images/Basilea/P14.JPG" ) );
        itemsImagelist.add(new ItemImageList("Basilea - Halloween", "http://andrespaez90.com/images/Basilea/P14.JPG"));
    }


    private void createList(ArrayList<ItemImageList> list){
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
            if(requestCode == RESULT_CANCELED){
                finish();
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

}
