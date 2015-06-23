package com.dev.innso.myappbum.UI;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.innso.myappbum.Utils.TAGs.SharedPrefKeys;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.SharePreferences;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileActivity extends ActionBarActivity {

    @InjectView(R.id.profile_image)
    ImageView profilePicture;

    @InjectView(R.id.profile_cover)
    ImageView profileCover;

    @InjectView(R.id.profile_name)
    TextView profileName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        init();
    }

    private void init(){
        String userId = SharePreferences.getApplicationValue(SharedPrefKeys.FACEBOOK_USERID);
        String userName = SharePreferences.getApplicationValue(SharedPrefKeys.NAME_USER);
        String userCover = SharePreferences.getApplicationValue(SharedPrefKeys.COVER_USER);

        if( userId != ""){
            String imageURL = "https://graph.facebook.com/"+userId+"/picture?type=large";
            Picasso.with(this).load(imageURL).placeholder(R.drawable.ic_profile).into(profilePicture);
            Picasso.with(this).load(userCover).placeholder(R.mipmap.default_bg).into(profileCover);

        }
        else{

        }
        profileName.setText(userName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
}
