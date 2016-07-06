package com.dev.innso.myappbum.models.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.ui.BaseDetailActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailPhotoActivity extends BaseDetailActivity implements Callback{

    public static final String EXTRA_URL = "url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        mBackground = (ImageView) findViewById(R.id.details_image);;

        String imageUrl = getIntent().getExtras().getString(EXTRA_URL);

        Picasso.with(this).load(imageUrl).into(mBackground, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onSuccess() {
        moveBackground();
    }

    @Override
    public void onError() {

    }
}


