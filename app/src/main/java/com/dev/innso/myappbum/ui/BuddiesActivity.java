package com.dev.innso.myappbum.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.models.ui.AppbumActivity;
import com.dev.innso.myappbum.ui.activities.PassNumber;
import com.dev.innso.myappbum.ui.fragments.AddPictureFragment;
import com.dev.innso.myappbum.ui.fragments.ListBuddiesFragment;
import com.dev.innso.myappbum.utils.tags.ActivityTags;
import com.dev.innso.myappbum.utils.tags.FragmentTags;


public class BuddiesActivity extends AppbumActivity {

    private boolean isOpenActivitiesActivated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddies);

        if (savedInstanceState == null) {
            manageFragment(ListBuddiesFragment.newInstance(isOpenActivitiesActivated), FragmentTags.LIST_BUDDIES, false);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);

        isPrivate();
    }


    private void isPrivate(){
        boolean pass = getIntent().getBooleanExtra("isPrivate", false);
        if( pass){
            Intent intent = new Intent( this, PassNumber.class);
            intent.putExtra("Pass",getIntent().getIntExtra("Pass",0));
            startActivityForResult(intent, ActivityTags.ACTIVITY_PASSNUMBER.getCode());
        }else{
            init();
        }
    }

    private void init(){
        getData();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getData(){
        Bundle extras = getIntent().getExtras();
        String appbumName = extras.getString("APPBUM_NAME");
        setTitle(appbumName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.menu_list_buddies, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listbuddies_picture:
                manageFragment(AddPictureFragment.newInstance(), FragmentTags.ADDPICTURE, false);
                break;
        }
        return false;
    }

    private ListBuddiesFragment getListBuddiesFragment() {
        return (ListBuddiesFragment) findFragmentByTag(FragmentTags.LIST_BUDDIES);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == ActivityTags.ACTIVITY_PASSNUMBER.getCode() ){
            if( resultCode == RESULT_CANCELED )
                finish();
        }
    }
}
