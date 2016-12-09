package com.dev.innso.myappbum.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.di.ApiModule;
import com.dev.innso.myappbum.di.component.AppComponent;
import com.dev.innso.myappbum.di.component.DaggerAppComponent;
import com.dev.innso.myappbum.managers.AppPreference;
import com.dev.innso.myappbum.managers.preferences.ManagerPreferences;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageViewCover, imageViewProfile;

    @Inject
    FirebaseUser currentUser;

    @Inject
    ManagerPreferences managerPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        AppComponent daggerComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();

        daggerComponent.inject(this);

        initViews();
    }


    private void initViews(){
        imageViewCover = (ImageView) findViewById(R.id.imageView_cover);

        imageViewProfile = (ImageView) findViewById(R.id.imageView_profile);

        initProfile();
    }

    private void initProfile() {

        if (currentUser != null) {

            if (managerPreferences.getBoolean(AppPreference.IS_FACEBOOK_USER)) {

                Uri imageURL = currentUser.getPhotoUrl();

                String urlCover = managerPreferences.getString(AppPreference.COVER_USER);

                Picasso.with(this).load(imageURL).into(imageViewProfile);

                Picasso.with(this).load(urlCover).into(imageViewCover);
            }
        }
    }
}
