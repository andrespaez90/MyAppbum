package com.dev.innso.myappbum.models.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.ui.BaseDetailActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseDetailActivity {

    public static final String EXTRA_URL = "url";

    @BindView(R.id.details_image)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        mBackground = mImageView;
        String imageUrl = getIntent().getExtras().getString(EXTRA_URL);
        Picasso.with(this).load(imageUrl).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
                moveBackground();
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}


