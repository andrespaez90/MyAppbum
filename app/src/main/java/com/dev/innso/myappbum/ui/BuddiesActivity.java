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
import com.dev.innso.myappbum.ui.fragments.AddpictureFragment;
import com.dev.innso.myappbum.ui.fragments.CustomizeFragment;
import com.dev.innso.myappbum.ui.fragments.ListBuddiesFragment;
import com.dev.innso.myappbum.utils.tags.ActivityTags;
import com.dev.innso.myappbum.utils.tags.FragmentTags;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BuddiesActivity extends AppbumActivity implements CustomizeFragment.OnCustomizeListener{

    private boolean isOpenActivitiesActivated = true;

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddies);
        if (savedInstanceState == null) {
            manageFragment(ListBuddiesFragment.newInstance(isOpenActivitiesActivated), FragmentTags.LIST_BUDDIES, false);
        }
        ButterKnife.bind(this);
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

        //MenuItem openActivities = menu.findItem(R.id.action_about);
        //openActivities.setChecked(isOpenActivitiesActivated);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listbuddies_picture:
                manageFragment(AddpictureFragment.newInstance(), FragmentTags.ADDPICTURE, false);
                break;
/*
            case R.id.action_reset:
                resetLayout();
                break;
            case R.id.action_customize:
                manageFragment(CustomizeFragment.newInstance(), FragmentTags.CUSTOMIZE, true);
                break;
            case R.id.action_about:
                startActivityWith(AboutActivity.class);
                break;*/
        }
        return false;
    }

    @Override
    public void setSpeed(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setSpeed(value);
        }
    }

    @Override
    public void setGap(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGap(value);
        }
    }

    @Override
    public void setGapColor(int color) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGapColor(color);
        }
    }

    @Override
    public void setDivider(Drawable drawable) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDivider(drawable);
        }
    }

    @Override
    public void setDividerHeight(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDividerHeight(value);
        }
    }

    @Override
    public void setAutoScrollFaster(int option) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setAutoScrollFaster(option);
        }
    }

    @Override
    public void setScrollFaster(int option) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setScrollFaster(option);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
    }

    private void resetLayout() {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.resetLayout();
            reset();
            CustomizeFragment customizeFragment = (CustomizeFragment) findFragmentByTag(FragmentTags.CUSTOMIZE);
            if (customizeFragment != null) {
                customizeFragment.reset();
            }
        }
    }

    private void reset() {
        //SharePreferences.reset();
    }

    public boolean onOpenActivitiesClick(MenuItem menuItem) {
        isOpenActivitiesActivated = !menuItem.isChecked();
        menuItem.setChecked(isOpenActivitiesActivated);
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setOpenActivities(isOpenActivitiesActivated);
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
